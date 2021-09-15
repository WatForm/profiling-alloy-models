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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts number of integer fields and constants in Alloy models
public class IntegerUse {
    //total counter for integer constants across all models
    private static int totConst = 0;
    //total counter for integer atoms in field declarations across all models
    private static int totIntDecl = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "database";
        System.out.println("Counting uses of Int in Alloy models in " + path);

        //file containing number of integer constants per model
        String fp_integerConst = directoryName + "intConst.txt";
        //file containing number of integers in field declarations per model
        String fp_integerDecls = directoryName + "intDecls.txt";

        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_integerConst));
            Files.deleteIfExists(Paths.get(fp_integerDecls));
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

                        //XPath string to find num constants in expressions
                        Collection<ParseTree> numTrees =  XPath.findAll(tree, "//expr/constant/num", parser);

                        //collecting all integer atoms used in relations
                        Collection<ParseTree> declNames =  XPath.findAll(tree, "//sigDecl//decls//decl//expr//name", parser);
                        List<String> intDecls = declNames.stream().map(ParseTree::getText).
                                filter(d -> d.equalsIgnoreCase("int")).collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("Integer Constants: " + numTrees.size());
                        System.out.println("Integer Fields: " + intDecls.size());

                        //incrementing total counters
                        totConst += numTrees.size();
                        totIntDecl += intDecls.size();

                        //writing results files
                        ResultWriter.writeResults(fp_integerConst, numTrees.size());
                        ResultWriter.writeResults(fp_integerDecls, intDecls.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total int constants: " + totConst);
            System.out.println("Total integers in decls: " + totIntDecl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
