package com.alloyprofiling.unused.linearreg;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.AssertionRetriever;
import com.alloyprofiling.retrievers.FunctionRetriever;
import com.alloyprofiling.retrievers.PredicateRetriever;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class CmdHolders {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Linear\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting commands and constraint holders in Alloy models in " + path);

        //file containing the number of predicate declarations in each model
        String fp_cmdHolders = directoryName + "cmdHolders.csv";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_cmdHolders));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fp_cmdHolders);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"cmds", "holders"};
            writer.writeNext(header);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating csv file");
        }


        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        //XPath hierarchy path to find command queries
                        Collection<ParseTree> commands = XPath.findAll(tree, "//cmdDecl", parser);
                        List<String>  functions = FunctionRetriever.getFunctions(parser, tree);
                        List<String> predicates = PredicateRetriever.getPredicates(parser,tree);
                        List<String> assertions = AssertionRetriever.getAssertions(parser, tree);

                        //combining all constraint holders into one list
                        Iterable<String> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(functions, predicates, assertions));

                        Collection<String> constraintHolders = Lists.newArrayList(combinedIterables);

                        if (commands.size() > 0 && constraintHolders.size() > 0) {
                            System.out.println(filePath.toFile());
                            System.out.println("Commands: " + commands.size());
                            System.out.println("Number of constraint holders: " + constraintHolders.size());

                            try {
                                // create FileWriter object with file as parameter
                                FileWriter outputfile = new FileWriter(file, true);

                                // create CSVWriter object filewriter object as parameter
                                CSVWriter writer = new CSVWriter(outputfile);

                                // add data to csv
                                String[] values = { Integer.toString(commands.size()),
                                        Integer.toString(constraintHolders.size())};
                                writer.writeNext(values);

                                // closing writer connection
                                writer.close();
                            }  catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
