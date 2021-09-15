package com.alloyprofiling.unused.linearreg;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.PredicateRetriever;
import com.opencsv.CSVWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Predicates {
    //class attribute containing the total number of predicates across all models
    private static int totPredicate = 0;
    //class attribute containing the total number of predicate calls across all models
    private static int totPC = 0;
    //class attribute containing the total number of models that contain predicate declarations
    private static int totPredModels = 0;
    private static int totMod = 0;

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
        System.out.println("Counting predicate decls/uses in Alloy models in " + path);

        //file containing the number of predicate declarations in each model
        String fp_predicates = directoryName + "predicates.csv";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_predicates));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fp_predicates);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"Declarations", "Uses"};
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

                        //XPath hierarchy path to identify "module" commands
                        Collection<ParseTree> moduleTrees = XPath.findAll(tree, "//module", parser);



                        //XPath string to extract predicate declaration parse trees
                        Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                        if (predTrees.size() > 0) {
                            totPredModels++;
                            //getting all predicate names from the model using PredicateRetriever
                            List<String> predicates = PredicateRetriever.getPredicates(parser, tree);

                            //pattern to find names under commands
                            ParseTreePattern pattern = parser.compileParseTreePattern("<name>",
                                    ALLOYParser.RULE_nameOrBlock);
                            List<ParseTreeMatch> matches_predUses = pattern.findAll(tree, "//cmdDecl/*");

                            List<ParseTree> trees_predUses = matches_predUses.stream().map(match -> match.get("name")).
                                    collect(Collectors.toList());
                            //filter checks if the collected names are predicates
                            List<String> predUses = trees_predUses.stream().map(ParseTree::getText).
                                    filter(predicates::contains).collect(Collectors.toList());

                            //pattern to find names in all expressions
                            Collection<ParseTree> predTrees_expr = XPath.findAll(tree, "//expr//name", parser);
                            //filter checks if the collected names are predicates
                            List<String> predUses_expr = predTrees_expr.stream().map(p -> p.getText()).
                                    filter(predicates::contains).collect(Collectors.toList());

                            //total number of predicate uses in a file:
                            //predicate calls in commands + predicate calls in expressions
                            int usesCount = predUses.size() + predUses_expr.size();
                            if (usesCount == 0)
                                return;
                            System.out.println(filePath.toFile());
                            System.out.println("Number of predicate declarations: " + predTrees.size());
                            totPredicate += predTrees.size();
                            System.out.println("Predicate uses: " + usesCount);
                            totPC += usesCount;

                            try {
                                // create FileWriter object with file as parameter
                                FileWriter outputfile = new FileWriter(file, true);

                                // create CSVWriter object filewriter object as parameter
                                CSVWriter writer = new CSVWriter(outputfile);

                                // add data to csv
                                String[] data1 = {Integer.toString(predTrees.size()), Integer.toString(usesCount)};
                                writer.writeNext(data1);

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
            System.out.println("Total number of predicates: " + totPredicate);
            System.out.println("Total predicate use: " + totPC);
            System.out.println("Total models that have predicates: " + totPredModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
