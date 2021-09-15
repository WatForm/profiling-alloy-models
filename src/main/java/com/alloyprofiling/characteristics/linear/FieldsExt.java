package com.alloyprofiling.characteristics.linear;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import com.opencsv.CSVWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FieldsExt {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Linear\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting #fields and #extensions sig for models in: " + path);

        //file containing the number of predicate declarations in each model
        String fp_fieldsExt = directoryName + "fieldsext.csv";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_fieldsExt));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fp_fieldsExt);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"extensions", "fields"};
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

                        /*
                        //XPath hierarchy path to identify "module" commands
                        Collection<ParseTree> moduleTrees = XPath.findAll(tree, "//module", parser);

                        //skipping files that are declared as modules
                        if (!moduleTrees.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            return;
                        } */

                        List<String> extensions = SigRetriever.getSigs("extensions", parser, tree);

                        List<String> fields = RelationRetriever.getRelations(parser, tree);

                        if (fields.size() > 0 && extensions.size() > 0) {
                            System.out.println(filePath.toFile());
                            System.out.println("Subsignature extensions: " + extensions.size());
                            System.out.println("Number of fields: " + fields.size());

                            try {
                                // create FileWriter object with file as parameter
                                FileWriter outputfile = new FileWriter(file, true);

                                // create CSVWriter object filewriter object as parameter
                                CSVWriter writer = new CSVWriter(outputfile);

                                // add data to csv
                                String[] data1 = {Integer.toString(extensions.size()), Integer.toString(fields.size())};
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
