package com.alloyprofiling.characteristics.constraints;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.PredicateRetriever;
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

public class Predicates {
    //class attribute containing the total number of predicates across all models
    private static int totPredicate = 0;
    //class attribute containing the total number of predicate calls across all models
    private static int totPC = 0;
    //class attribute containing the total number of models that contain predicate declarations
    private static int totPredModels = 0;
    //class attribute containing the total number of predicate uses in commands
    private static int totUsesCmds = 0;
    //class attribute containing the total number of function uses in formulas
    private static int totUsesExprs = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Constraints\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting predicate decls/uses in Alloy models in " + path);

        //file containing the number of predicate declarations in each model
        String fp_predicates = directoryName + "predicateDecls.txt";
        //file containing the number of predicate calls in commands in each model
        String fp_predUseCmds = directoryName + "predicateUsesCmds.txt";
        //file containing the number of predicate calls in formulas in each model
        String fp_predUseExprs = directoryName + "predicateUsesExprs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_predUseCmds));
            Files.deleteIfExists(Paths.get(fp_predUseExprs));
            Files.deleteIfExists(Paths.get(fp_predicates));
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

                        //XPath string to extract predicate declaration parse trees
                        Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                        if (predTrees.size() > 0)
                            totPredModels++;

                        //getting all predicate names from the model using PredicateRetriever
                        List<String> predicates = PredicateRetriever.getPredicates(parser, tree);

                        //XPath hierarchy path to find names under commands
                        List<String> names_cmd = XPath.findAll(tree, "//cmdDecl/nameOrBlock/name",parser).stream()
                                .map(ParseTree::getText).collect(Collectors.toList());

                        //filter checks if the collected names are predicates
                        List<String> predUses_cmds = names_cmd.stream().filter(predicates::contains).collect(Collectors.toList());

                        //pattern to find names in all expressions
                        Collection<ParseTree> predTrees_expr =  XPath.findAll(tree, "//expr//name", parser);
                        //filter checks if the collected names are predicates
                        List<String> predUses_expr = predTrees_expr.stream().map(p -> p.getText()).
                                filter(predicates::contains).collect(Collectors.toList());

                        //total number of predicate uses in a file:
                        //predicate calls in commands + predicate calls in expressions
                        int usesCount = predUses_cmds.size() + predUses_expr.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of predicate declarations: " + predTrees.size());
                        totPredicate += predTrees.size();
                        System.out.println("Predicate uses: " + usesCount);
                        System.out.println("Predicates uses in commands: " + predUses_cmds.size());
                        System.out.println("Predicates uses in formulas: " + predUses_expr.size());
                        //incrementing counters
                        totPC += usesCount;
                        totUsesCmds += predUses_cmds.size();
                        totUsesExprs += predUses_expr.size();

                        //writing result file containing the number of predicates in each file
                        ResultWriter.writeResults(fp_predicates, predTrees.size());
                        //writing result file containing the number of predicate uses in each file
                        ResultWriter.writeResults(fp_predUseCmds, predUses_cmds.size());
                        ResultWriter.writeResults(fp_predUseExprs, predUses_expr.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of predicates: " + totPredicate);
            System.out.println("Total predicate use: " + totPC);
            System.out.println("Total predicate uses in commands: " + totUsesCmds);
            System.out.println("Total predicate uses in formulas: " + totUsesExprs);
            System.out.println("Total models that have predicates: " + totPredModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
