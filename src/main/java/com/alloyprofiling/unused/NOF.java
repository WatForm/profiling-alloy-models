package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NOF {
    //class attribute representing the total number of fields across all models
    private static int totFields = 0;
    public static void main(String[] args) {
        //repository of models
        String path = "alloy_models";
        System.out.println("Counting the number of fields in Alloy models in " + path);

        //file containing the number of fields in each model
        String fp_NOF = "Results\\QualityIndicators\\NOF.txt";
        //file containing the total number of fields across all models
        String fp_totFields = "Results\\QualityIndicators\\totFields.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_NOF));
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

                        //XPath string to extract all field trees under signature declarations
                        Collection<ParseTree> fieldTrees = XPath.findAll(tree, "//sigDecl//decls//decl", parser);
                        //Counting fields (and accounting for multiple field declarations grouped in one declaration
                        //e.g. f1, f2: A counts as 2)
                        List<Integer> fieldCounts = fieldTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                                .map(n -> n.split(",").length).collect(Collectors.toList());

                        //summing all field counts to get the total number of fields in a model
                        int field_count = fieldCounts.stream().mapToInt(Integer::intValue).sum();

                        System.out.println(filePath.toFile());
                        System.out.println("Number of fields: " + field_count);

                        totFields += field_count;

                        //writing result file containing the number of fields in each file
                        try {
                            FileWriter nofWriter = new FileWriter(fp_NOF, true);
                            nofWriter.write(Integer.toString(field_count) + '\n');
                            nofWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of fields: " + totFields);
            //writing total number of fields
            FileWriter totWriter = new FileWriter(fp_totFields, true);
            totWriter.write(Integer.toString(totFields) + '\n');
            totWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
