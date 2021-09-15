package com.alloyprofiling.unused.linearreg;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.characteristics.length.ModelLength;
import com.opencsv.CSVWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Computes the length (not including blank lines) of Alloy models in a given repository
public class LengthModules {
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
        System.out.println("Counting non-empty lines for Alloy models in  " + path);

        //file containing the number of lines in each model
        String fp_lengthmodules = directoryName + "lengthmodules.csv";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_lengthmodules));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(fp_lengthmodules);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"length", "modules"};
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
                        //counting non-empty lines in alloy model
                        long length = ModelLength.getLength(String.valueOf(filePath));

                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        //pattern to find open commands
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");


                        List<String> moduleNames = matches.stream().map(match -> match.get("name").getText()).
                                collect(Collectors.toList());

                        //extracting user-declared modules (i.e. do not contain "util")
                        List<String> userModules = moduleNames.stream().filter(n-> !(n.contains("util"))).
                                collect(Collectors.toList());



                        if (length > 0 && !userModules.isEmpty()) {

                            System.out.println(filePath.toFile());
                            System.out.println("Model Length: " + length);
                            System.out.println("User-created modules: " + userModules.size());

                            try {
                                // create FileWriter object with file as parameter
                                FileWriter outputfile = new FileWriter(file, true);

                                // create CSVWriter object filewriter object as parameter
                                CSVWriter writer = new CSVWriter(outputfile);

                                // add data to csv
                                String[] data1 = {Long.toString(length), Integer.toString(userModules.size())};
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
