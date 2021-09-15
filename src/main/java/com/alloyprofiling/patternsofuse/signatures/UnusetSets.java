package com.alloyprofiling.patternsofuse.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.SigRetriever;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnusetSets {
    private static int totUnused = 0;
    private static int totMod = 0;
    public static void main(String[] args) {
        //repository of models
        String path = "alloy_models";
        System.out.println("Counting unused sets in Alloy models in " + path);

        //file containing the number of unused sets/relations in each model
        String fp_USRC = "Results\\ModelingPractices\\unusedSets.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_USRC));
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

                        //XPath string to identify "module" commands
                        Collection<ParseTree> moduleTrees = XPath.findAll(tree, "//module", parser);

                        //skipping files that are declared as modules
                        if (!moduleTrees.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            totMod += 1;
                            return;
                        }

                        //XPath string to get all field names
                        Collection<ParseTree> fieldTrees = XPath.findAll(tree, "//sigDecl/decls/decl", parser);

                        //post-processing to pull out all field names i.e. f1,f2: Int counts as 2
                        List<String[]> field_arrays = fieldTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                                .map(n -> n.split(",")).collect(Collectors.toList());
                        List<String> fieldNames = new ArrayList<>();
                        for (String[] f: field_arrays) {
                            fieldNames.addAll(Arrays.asList(f));
                        }

                        //getting all signatures using SigRetriever
                        List<String> totalSigs = SigRetriever.getSigs("all", parser, tree);

                        //getting expressions under facts, assertions, functions and predicates
                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);
                        Collection<ParseTree> exprAsserts = XPath.findAll(tree, "//assertDecl//expr", parser);
                        Collection<ParseTree> exprFuns = XPath.findAll(tree, "//funDecl//expr", parser);
                        Collection<ParseTree> exprPreds = XPath.findAll(tree, "//predDecl//expr", parser);

                        //combining all expressions parse trees into one list
                        Iterable<ParseTree> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(exprFacts, exprAsserts, exprFuns, exprPreds));

                        List<String> exprs = Lists.newArrayList(combinedIterables).
                                stream().map(ParseTree::getText).collect(Collectors.toList());

                        List<String> unused_setRel = new ArrayList<>();

                        //checking if a declared signature is not used
                        for (String sig: totalSigs) {
                            if (exprs.stream().noneMatch(e -> e.contains(sig)))
                                unused_setRel.add(sig);
                        }

                        //checking if a declared relation (i.e. field) is not used
                        for (String field: fieldNames) {
                            if (exprs.stream().noneMatch(e -> e.contains(field)))
                                unused_setRel.add(field);
                        }

                        //incrementing totUnused
                        totUnused += unused_setRel.size();

                        System.out.println(filePath.toFile());
                        System.out.println("Unused sets/relations: " + unused_setRel.size());

                        //writing to results file
                        try {
                            FileWriter unusedWrite = new FileWriter(fp_USRC, true);
                            unusedWrite.write(Integer.toString(unused_setRel.size())  + '\n');
                            unusedWrite.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of unused signatures: " + totUnused);
            System.out.println("Total user-created modules skipped: " + totMod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
