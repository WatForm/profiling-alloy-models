package com.alloyprofiling.characteristics.constraintholders;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
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

public class Facts {
    private static int totFacts = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\ConstraintHolders\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting number of fact blocks in Alloy models in " + path);

        //file containing the number of extension signatures in each model
        String fp_facts = directoryName + "facts.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_facts));
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

                        //pattern to find signature declarations with a fact block
                        ParseTreePattern p_sigFacts= parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_sigFacts = p_sigFacts.findAll(tree, "//paragraph/*");

                        List<String> matches_str = matches_sigFacts.stream().map(m -> m.get("block").getText().
                                replaceAll("\\s","")).filter(s -> !s.equals("{}")).
                                collect(Collectors.toList());

                        //XPath string to get fact block declarations
                        Collection<ParseTree> factTrees =  XPath.findAll(tree, "//factDecl", parser);

                        int factBlocks = matches_str.size() + factTrees.size();

                        System.out.println(filePath.toFile());
                        System.out.println("Fact blocks: " + factBlocks);
                        totFacts += factBlocks;

                        //writing result file containing the number of fact blocks in each model
                        ResultWriter.writeResults(fp_facts, factBlocks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of fact blocks: " + totFacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
