package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that computes the average number of fields per signature in Alloy models
public class AverageFields {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Signatures\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        //repository of models
        String path = "corpus";
        System.out.println("Comptuing #fields/#sigsThatHaveFields for Alloy models in " + path);

        //file containing the number of fields in each model
        String fp_avgFieldCount = directoryName + "avgFields.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_avgFieldCount));
        } catch (IOException e) {
            e.printStackTrace();
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

                        //XPath hierarchy path to extract all signature declarations
                        Collection<ParseTree> sigTrees = XPath.findAll(tree, "//sigDecl", parser);

                        //field counter
                        int field_count = 0;
                        //signatures with fields counter
                        int sig_count = 0;
                        //iterating over signature declaration parse trees
                        for (ParseTree sig: sigTrees) {
                            //XPath string to extract all field trees under signature declarations
                            Collection<ParseTree> fieldTrees = XPath.findAll(sig, "//sigDecl//decls//decl", parser);

                            if (!fieldTrees.isEmpty()) {
                                sig_count++;

                                //Counting fields (and accounting for multiple field declarations grouped in one declaration
                                //e.g. f1, f2: A counts as 2)
                                List<Integer> fieldCounts = fieldTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                                        .map(n -> n.split(",").length).collect(Collectors.toList());

                                //summing all field counts to get the total number of fields in a model
                                field_count += fieldCounts.stream().mapToInt(Integer::intValue).sum();
                            }
                        }

                        System.out.println(filePath.toFile());
                        if (field_count != 0 && sig_count != 0) {
                            System.out.println("field_count: " + field_count);
                            System.out.println("sig_count: " + sig_count);
                            System.out.println("#fields/#sigsThatHaveFields: " + (float) field_count / sig_count);

                            //writing result file containing the average number of fields per signature in each file
                            try {
                                FileWriter nofWriter = new FileWriter(fp_avgFieldCount, true);
                                nofWriter.write(Float.toString((float)  field_count/ sig_count) + '\n');
                                nofWriter.close();
                            } catch (IOException e) {
                                System.out.println("An error writing files occurred.");
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
