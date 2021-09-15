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

public class BitshiftingOperators {
    private static int totBSO = 0;
    private static int totBSOModels = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting bit shifter operators in Alloy models in " + path);

        //result file containing the number of bit shifting operators in each model
        String fp_bso = directoryName + "bitshift.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_bso));
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

                        //XPath hierarchy string to extract bit shifting operators
                        Collection<ParseTree> bsoTrees =  XPath.findAll(tree, "//expr/binOp/bit_shifter_operators", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Number of bit shifter operators: " + bsoTrees.size());
                        totBSO += bsoTrees.size();

                        if (bsoTrees.size() > 0)
                            totBSOModels++;

                        //writing result file containing the number of bit shifting operators in each file
                        ResultWriter.writeResults(fp_bso, bsoTrees.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of bit shifter operators: " + totBSO);
            System.out.println("Total number of models that use bit shifter operators: " + totBSOModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
