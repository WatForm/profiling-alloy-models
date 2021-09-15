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
import java.util.stream.Stream;

public class PredicateDecls {
    //class attribute containing the total number of predicates across all models
    private static int totPredicate = 0;
    public static void main(String[] args) {
        //repository of models
        String path = "database";
        System.out.println("Counting predicate declarations in Alloy models in " + path);

        //file containing the number of predicate declarations in each model
        String fp_predicates = "Results\\QualityIndicators\\PDC.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_predicates));
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

                        //XPath string to extract predicate declaration parse trees
                        Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Number of predicates: " + predTrees.size());
                        totPredicate += predTrees.size();

                        //writing result file containing the number of predicates in each file
                        try {
                            FileWriter pdcWriter = new FileWriter(fp_predicates, true);
                            pdcWriter.write(Integer.toString(predTrees.size()) + '\n');
                            pdcWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of predicates: " + Integer.toString(totPredicate));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
