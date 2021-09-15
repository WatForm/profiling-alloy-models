package com.alloyprofiling.characteristics.constraintholders;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.FunctionRetriever;
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

public class Functions {
    //class attribute containing the total number of functions across all models
    private static int totFunctions = 0;
    //class attribute for total number of functions uses across all models
    private static int totFCC = 0;
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
        System.out.println("Counting function decls/uses in Alloy models in " + path);

        //file containing the number of function declarations in each model
        String fp_functions = directoryName + "functionDecls.txt";
        //file containing the number of function calls for each model
        String fp_funUse = directoryName + "functionUses.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_funUse));
            Files.deleteIfExists(Paths.get(fp_functions));
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

                        //XPath string to get function declaration parse trees
                        Collection<ParseTree> funcTrees =  XPath.findAll(tree, "//funDecl", parser);

                        //using FunctionRetriever to get a list of function names in a model
                        List<String> functions = FunctionRetriever.getFunctions(parser, tree);

                        //pattern to find functions names under command declarations
                        ParseTreePattern pattern = parser.compileParseTreePattern("<name>", ALLOYParser.RULE_nameOrBlock);
                        List<ParseTreeMatch> matches_funUses = pattern.findAll(tree, "//cmdDecl/*");

                        //extracting function names from the command matches
                        List<ParseTree> trees_funUses = matches_funUses.stream().map(match -> match.get("name")).
                                collect(Collectors.toList());
                        //filter checks if the collected names are functions
                        List<String> funUses = trees_funUses.stream().map(ParseTree::getText).filter(functions::contains).
                                collect(Collectors.toList());

                        //pattern to find functions names in all expressions
                        Collection<ParseTree> funTrees_expr =  XPath.findAll(tree, "//expr//name", parser);
                        //filter checks if the collected names are functions
                        List<String> funUses_expr = funTrees_expr.stream().map(p -> p.getText()).
                                filter(functions::contains).collect(Collectors.toList());

                        int countFun = funUses.size() + funUses_expr.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of functions: " + funcTrees.size());
                        totFunctions += funcTrees.size();
                        System.out.println("Function uses: " + countFun);
                        totFCC += countFun;

                        //writing result file containing the number of functions in each file
                        ResultWriter.writeResults(fp_functions, funcTrees.size());
                        //writing result file containing the number of functions uses in each file
                        ResultWriter.writeResults(fp_funUse, countFun);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of functions: " + totFunctions);
            System.out.println("Total function use: " + totFCC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
