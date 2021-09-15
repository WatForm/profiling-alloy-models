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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of occurrences of each set constant and the number of models
//that use each constant
public class Constants {
    //total counter for each set constant (across all models)
    private static int totConstants = 0;
    private static int totNone = 0;
    private static int totUniv = 0;
    private static int totIden = 0;
    private static  int totModels = 0;
    /**
     * Appends "1" to the txt file in filePath if the count > 0
     * @param count number of occurrences of a constant
     * @param filePath path to the results file
     */
    private static void countModels(Integer count, String filePath) {
        if (count > 0){
            ResultWriter.writeResults(filePath, 1);
            totModels++;
        }
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting use of constants in Alloy models in " + path);

        //files containing the number of uses of each constants
        String fp_constants = directoryName + "constants.txt";
        String fp_none = directoryName + "none.txt";
        String fp_univ = directoryName + "univ.txt";
        String fp_iden = directoryName + "iden.txt";

        //files containing the number of models that use each constant
        String fp_noneModels = directoryName + "noneModels.txt";
        String fp_univModels = directoryName + "univModels.txt";
        String fp_idenModels = directoryName + "idenModels.txt";

        //deleting result files if they already exist
        try {
            List<String> files = Arrays.asList(fp_constants, fp_none, fp_univ, fp_iden, fp_noneModels,
                    fp_univModels, fp_idenModels);
            for (String f: files) {
                Files.deleteIfExists(Paths.get(f));
            }
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

                        //XPath hierarchy path to extract constants
                        Collection<ParseTree> constantTrees =  XPath.findAll(tree, "//expr/constant/const_sets", parser);

                        //post-processing to tally up the occurrences of each constant
                        List<String> none = constantTrees.stream().map(ParseTree::getText).filter(c -> c.equals("none"))
                                .collect(Collectors.toList());
                        List<String> univ = constantTrees.stream().map(ParseTree::getText).filter(c -> c.equals("univ"))
                                .collect(Collectors.toList());
                        List<String> iden = constantTrees.stream().map(ParseTree::getText).filter(c -> c.equals("iden"))
                                .collect(Collectors.toList());

                        //counting models that have constants
                        countModels(none.size(), fp_noneModels);
                        countModels(univ.size(), fp_univModels);
                        countModels(iden.size(), fp_idenModels);

                       //incrementing total counters for each constant
                        totNone += none.size();
                        totUniv += univ.size();
                        totIden += iden.size();

                        System.out.println(filePath.toFile());
                        System.out.println("Number of constants: " + constantTrees.size());

                        totConstants += constantTrees.size();

                        //writing result file containing the number of constants in each file
                        ResultWriter.writeResults(fp_constants, constantTrees.size());
                        ResultWriter.writeResults(fp_none, none.size());
                        ResultWriter.writeResults(fp_univ, univ.size());
                        ResultWriter.writeResults(fp_iden, iden.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of constants: " + totConstants);
            System.out.println("Total number of none: " + totNone);
            System.out.println("Total number of univ: " + totUniv);
            System.out.println("Total number of iden: " + totIden);
            System.out.println("Total number of models that use constants: " + totModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
