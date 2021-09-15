package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// SCRIPT NOT USED
// MERGED INTO SRG
// FRI measures the coupling between signatures at the formula (in facts) level
public class FRI {
    private static int totCS = 0;
    private static List<String> getSigNames(ParseTreeMatch match) {
        //post-processing to pull out all sig names e.g. <mult> sig A, B { } counts as 2 sigs
        List<String> sigNames = new ArrayList<>();
        String[] namesList = match.get("names").getText().split(",");
        Collections.addAll(sigNames, namesList);
        return  sigNames;
    }
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing FRI for Alloy models in " + path);

        //deleting results file if it already exists
        String fp_FRI = "Results\\QualityIndicators\\FRI.txt";
        try {
            Files.deleteIfExists(Paths.get(fp_FRI));
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

                        ParseTreePattern p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");

                        List<String> relations = RelationRetriever.getRelations(parser, tree);
                        List<String> sets = SigRetriever.getSigs("all", parser, tree);

                        List<String> coupledSigs = new ArrayList<>();
                        for (ParseTreeMatch m: matches) {
                            List<String> otherRelations = new ArrayList<>(relations);
                            //removing each signature's relations to get relations that belong to other signatures
                            List<String> mRelations = XPath.findAll(m.getTree(), "//decls//decl", parser).
                                    stream().map(ParseTree::getText).collect(Collectors.toList());
                            List<String> mRelNames = new ArrayList<>();
                            mRelations.stream().forEach(r -> mRelNames.addAll(Arrays.asList(r.split(":")[0].
                                    split(","))));
                            otherRelations.removeAll(mRelNames);
                            List<String> otherSets = new ArrayList<>(sets);
                            //removing each signature's name to get the names of other signatures
                            otherSets.removeAll(getSigNames(m));

                            ParseTree block = m.get("block");
                            List<String> block_sets = XPath.findAll(block, "//expr//name", parser).stream().
                                    filter(n -> sets.contains(n.getText()) || relations.contains(n.getText())).
                                    map(ParseTree::getText).collect(Collectors.toList());

                            if (otherRelations.stream().anyMatch(block_sets::contains) ||
                            otherSets.stream().anyMatch(block_sets::contains))
                                coupledSigs.addAll(getSigNames(m));
                        }


                        System.out.println(filePath.toFile());
                        System.out.println("Coupled sigs: " + coupledSigs);
                        totCS += coupledSigs.size();


                        try {
                            FileWriter friWrite = new FileWriter(fp_FRI, true);
                            friWrite.write(Integer.toString(coupledSigs.size())  + '\n');
                            friWrite.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total coupled sigs: " + totCS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
