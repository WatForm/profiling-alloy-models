package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
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

//class that counts the number of unused sets in Alloy models
public class UnusedSets {
    //total number of unused sets over all models
    private static int totUnused = 0;
    //total number of files declared as modules
    private static int totMod = 0;
    //total number of fields/relations
    private static int totFields = 0;
    private static int totModelsUnusedSets = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        //repository of models
        String path = "corpus";
        System.out.println("Counting unused sets in Alloy models in " + path);

        //file containing the number of unused sets/relations in each model
        String fp_unusedSets = directoryName + "unusedSets.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_unusedSets));
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

                        //XPath hierarchy path to identify "module" commands
                        Collection<ParseTree> moduleTrees = XPath.findAll(tree, "//module", parser);

                        //skipping files that are declared as modules
                        if (!moduleTrees.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            //incrementing counter
                            totMod += 1;
                            return;
                        }

                        //XPath hierarchy path to get all field names
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

                        //getting expressions under facts, assertions, functions, predicates and macros
                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);
                        Collection<ParseTree> exprAsserts = XPath.findAll(tree, "//assertDecl//expr", parser);
                        Collection<ParseTree> exprFuns = XPath.findAll(tree, "//funDecl//expr", parser);
                        Collection<ParseTree> exprPreds = XPath.findAll(tree, "//predDecl//expr", parser);
                        Collection<ParseTree> exprMacros = XPath.findAll(tree, "//macro//macro_expr//expr", parser);
                        Collection<ParseTree> declsMacros = XPath.findAll(tree, "//macro//macro_expr//decls//decl", parser);
                        Collection<ParseTree> exprSigFacts = XPath.findAll(tree, "//sigDecl//block_opt//block//expr", parser);

                        //combining all expressions parse trees into one list
                        Iterable<ParseTree> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(exprFacts, exprAsserts, exprFuns, exprPreds, exprMacros, declsMacros, exprSigFacts));

                        List<String> exprs = Lists.newArrayList(combinedIterables).
                                stream().map(ParseTree::getText).collect(Collectors.toList());

                        //list to be populated with the names of unused sets
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

                        totFields += RelationRetriever.getRelations(parser, tree).size();

                        System.out.println(filePath.toFile());
                        System.out.println("Unused sets/relations: " + unused_setRel.size());

                        if (!unused_setRel.isEmpty())
                            totModelsUnusedSets++;

                        //writing to results file
                        ResultWriter.writeResults(fp_unusedSets, unused_setRel.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //printing total number of unused signatures, user-created modules and fields
            System.out.println("Total number of unused signatures: " + totUnused);
            System.out.println("Total user-created modules skipped: " + totMod);
            //total number of fields/relations is printed in this script because there is no script
            //that just counts fields/relation
            System.out.println("Total number of fields/relations: " + totFields);
            System.out.println("Total number of models with unused sets: " + totModelsUnusedSets);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
