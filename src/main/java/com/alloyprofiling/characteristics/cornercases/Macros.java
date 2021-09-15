package com.alloyprofiling.characteristics.cornercases;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

//class that counts the number of untyped macros used in Alloy models and the number of models that use
//untyped macros
public class Macros {
    //total counter for the number of macros (across all Alloy models)
    private static int totMacros = 0;
    //total counter for the number of models that use untyped macros
    private static  int totMacroModels = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting untyped macros in Alloy models in " + path);

        //result file containing the number of bit shifting operators in each model
        String fp_macros = directoryName + "macros.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_macros));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        //XPath hierarchy string to extract untyped macros parse trees
                        Collection<ParseTree> macroTrees =  XPath.findAll(tree, "//macro", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Number of untyped macros: " + macroTrees.size());
                        totMacros += macroTrees.size();

                        //incrementing models counter by 1 if model uses untyped macros
                        if (macroTrees.size() > 0) {
                            totMacroModels++;
                        }
                        //writing result file containing the number of untyped macros in each file
                        ResultWriter.writeResults(fp_macros, macroTrees.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of untyped macros: " + totMacros);
            System.out.println("Total number of models that use untyped macros: " + totMacroModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
