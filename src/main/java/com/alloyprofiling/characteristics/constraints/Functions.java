package com.alloyprofiling.characteristics.constraints;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.FunctionRetriever;
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

public class Functions {
    //class attribute containing the total number of functions across all models
    private static int totFunctions = 0;
    //class attribute for total number of functions uses across all models
    private static int totFCC = 0;
    //class attribute containing the total number of models that contain function declarations
    private static int totFuncModels = 0;
    //class attribute containing the total number of function uses in commands
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
        System.out.println("Counting function decls/uses in Alloy models in " + path);

        //file containing the number of function declarations in each model
        String fp_functions = directoryName + "functionDecls.txt";
        //file containing the number of function calls for each model
        String fp_funUseCmds = directoryName + "functionUsesCmds.txt";
        String fp_funUseExprs = directoryName + "functionUsesExprs.txt";


        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_funUseCmds));
            Files.deleteIfExists(Paths.get(fp_funUseExprs));
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

                        if (funcTrees.size() > 0)
                            totFuncModels++;

                        //using FunctionRetriever to get a list of function names in a model
                        List<String> functions = FunctionRetriever.getFunctions(parser, tree);

                        //XPath hierarchy path to find functions names under commands
                        List<String> names_cmd = XPath.findAll(tree, "//cmdDecl/nameOrBlock/name",parser).stream()
                                .map(ParseTree::getText).collect(Collectors.toList());

                        //filter checks if the collected names are functions
                        List<String> funUses_cmds = names_cmd.stream().filter(functions::contains).collect(Collectors.toList());

                        //pattern to find names in all expressions
                        Collection<ParseTree> funTrees_expr =  XPath.findAll(tree, "//expr//name", parser);
                        //filter checks if the collected names are functions
                        List<String> funUses_expr = funTrees_expr.stream().map(p -> p.getText()).
                                filter(functions::contains).collect(Collectors.toList());

                        int countFun = funUses_cmds.size() + funUses_expr.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of functions: " + funcTrees.size());
                        totFunctions += funcTrees.size();
                        System.out.println("Function uses: " + countFun);
                        System.out.println("Function uses in commands: " + funUses_cmds.size());
                        System.out.println("Function uses in formulas: " + funUses_expr.size());
                        totFCC += countFun;
                        totUsesCmds += funUses_cmds.size();
                        totUsesExprs += funUses_expr.size();

                        //writing result file containing the number of functions in each file
                        ResultWriter.writeResults(fp_functions, funcTrees.size());
                        //writing result file containing the number of functions uses in each file
                        ResultWriter.writeResults(fp_funUseCmds, funUses_cmds.size());
                        ResultWriter.writeResults(fp_funUseExprs, funUses_expr.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of functions: " + totFunctions);
            System.out.println("Total function use: " + totFCC);
            System.out.println("Total function uses in commands: " + totUsesCmds);
            System.out.println("Total function uses in formulas: " + totUsesExprs);
            System.out.println("Total models that have functions: " + totFuncModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
