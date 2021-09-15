package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.RelationRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InheritedFields {
    //class attribute representing the total number of fields across all models
    private static int totInheritedFields = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Signatures\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "C:\\Users\\eeid\\Desktop\\subModels";
        System.out.println("Counting the number of inherited fields in Alloy models in " + path);

        //file containing the number of fields in each model
        String fp_fields = directoryName + "inheritedFields.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_fields));
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


                        List<String> fields = RelationRetriever.getRelations(parser, tree);

                        List<String> names = XPath.findAll(tree, "//expr//name", parser).stream().
                                map(ParseTree::getText).collect(Collectors.toList());

                        List<String> inheritedFields = names.stream().filter(fields::contains).
                                collect(Collectors.toList());

                        System.out.println(filePath.toFile());
                        System.out.println(fields);
                        System.out.println("Inherited fields: " + inheritedFields.size() + " " + inheritedFields);
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of inherited fields: " + totInheritedFields);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
