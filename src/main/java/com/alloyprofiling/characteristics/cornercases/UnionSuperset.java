package com.alloyprofiling.characteristics.cornercases;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of union subsets in Alloy model
public class UnionSuperset {
    //total counter for union subsets (across all models)
    private static int totUE = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting subsets of union in Alloy models in " + path);

        //file containing the number of union supersets
        String fp_unionSubset = directoryName + "unionSuperset.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_unionSubset));
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

                        //pattern to find signature declaration with a union superset
                        ParseTreePattern p = parser.compileParseTreePattern
                                ("<priv> <abs_multiplicity> sig <names> in <union> { <decls> } <block_opt>",
                                        ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");

                        List<String> perFileTot = new ArrayList<>();

                        //post-processing to pull out all sig names i.e. one sig A, B in C + D  counts as 2 sigs
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> oneSigs = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : oneSigs) {
                            String[] namesList = names.split(",");
                            Collections.addAll(perFileTot, namesList);

                        }

                        System.out.println(filePath.toFile());
                        totUE += perFileTot.size();
                        System.out.println("Number of union subsets: " + perFileTot.size());

                        //writing result file containing the number of union supersets in each file
                        ResultWriter.writeResults(fp_unionSubset, perFileTot.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of union subsets: " + Integer.toString(totUE));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
