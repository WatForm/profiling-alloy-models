package com.alloyprofiling.characteristics.sets;

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

//class that counts the number of abstract signatures in Alloy models
public class AbstractSigs {
    //counter for total number of abstract signatures (across all models)
    private static int totalAbsCount = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting abstract sigs in Alloy models in " + path);

        //file containing the number of abstract sigs in each model
        String fp_absSigs = directoryName + "absSigs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_absSigs));
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

                        //pattern to find abstract signatures
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> abstract sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");

                        //pattern to find child signatures
                        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

                        //getting names of parent sigs
                        List<ParseTree> allNames_parent = matches_children.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> parentSigs = allNames_parent.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //extracting the names of abstract signatures
                        List<String> totalAbsSigs = new ArrayList<>();
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> absSigs = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : absSigs) {
                            String[] namesList = names.split(",");
                            Collections.addAll(totalAbsSigs, namesList);
                        }

                        System.out.println(filePath.toFile());
                        totalAbsCount += totalAbsSigs.size();
                        System.out.println("Number of Abstract Sigs: " + totalAbsSigs.size());

                        //writing result files containing number of abstract sigs in each model
                        ResultWriter.writeResults(fp_absSigs, totalAbsSigs.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Abstract Signatures: " + totalAbsCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
