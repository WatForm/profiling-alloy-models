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

public class FunctionDecls {
    //class attribute for total number of functions declarations across all models
    private static int totFunctions = 0;
    public static void main(String[] args) {
        //repository of models
        String path = "database";
        System.out.println("Counting function declarations in Alloy models in " + path);

        //file containing the number of function declarations in each model
        String fp_functions = "Results\\QualityIndicators\\FDC.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_functions));
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

                        //XPath string to get function declaration parse trees
                        Collection<ParseTree> funcTrees =  XPath.findAll(tree, "//funDecl", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Number of functions: " + funcTrees.size());
                        totFunctions += funcTrees.size();

                        //writing result file containing the number of predicates in each file
                        try {
                            FileWriter myWriter = new FileWriter(fp_functions, true);
                            myWriter.write(Integer.toString(funcTrees.size()) + '\n');
                            myWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of functions: " + totFunctions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
