package com.alloyprofiling.characteristics.constraints;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.FunctionRetriever;
import com.alloyprofiling.retrievers.PredicateRetriever;
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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that labels formulas in Alloy model according to the style used
public class FormulasExpanded {
    //counters for each pure/dominant style (across all models)
    private static  int tot_formulas = 0;


    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Formulas\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting expanded top-level formulas in Alloy models in " + path);

        String fp_formulaCount = directoryName + "formulaCount_expanded.txt";

        try {
            Files.deleteIfExists(Paths.get(fp_formulaCount));
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

                        int formula_count = 0;
                        LinkedHashMap<String, Integer> pred_uses_map = PredicateRetriever.countPredicateUses(parser, tree);
                        LinkedHashMap<String, Integer> func_uses_map = FunctionRetriever.countFunctionUses(parser, tree);

                        Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                        for (ParseTree t: predTrees) {
                            int pred_formula_count = 0;
                            List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                                    .collect(Collectors.toList());
                            String name = names.get(0);
                            Collection<ParseTree> exprPreds = XPath.findAll(t, "//expr", parser);
                            for (ParseTree f: exprPreds) {
                                //identifying top-level formulas
                                if (f.getParent() instanceof ALLOYParser.BlockContext ||
                                        f.getParent() instanceof  ALLOYParser.FunExprContext) {
                                    pred_formula_count++;
                                }
                            }
                            try {
                                formula_count += pred_uses_map.get(name) * pred_formula_count;
                            } catch (Exception e) {
                                System.out.println("Predicate not used");
                                formula_count += pred_formula_count;
                            }
                        }

                        Collection<ParseTree> funcTrees = XPath.findAll(tree, "//funDecl", parser);

                        for (ParseTree t: funcTrees ) {
                            int func_formula_count = 0;
                            List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                                    .collect(Collectors.toList());
                            String name = names.get(0);
                            Collection<ParseTree> funcExprs = XPath.findAll(t, "//expr", parser);
                            for (ParseTree f: funcExprs) {
                                //identifying top-level formulas
                                if (f.getParent() instanceof ALLOYParser.BlockContext ||
                                        f.getParent() instanceof  ALLOYParser.FunExprContext) {
                                    func_formula_count++;
                                }
                            }
                            try {
                                formula_count += func_uses_map.get(name) * func_formula_count;
                            } catch (Exception e) {
                                System.out.println("Function not used");
                                formula_count += func_formula_count;
                            }
                        }

                        //extracting formulas under facts, assertions, functions, predicates, macros and signature facts
                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);
                        Collection<ParseTree> exprAsserts = XPath.findAll(tree, "//assertDecl//expr", parser);
                        Collection<ParseTree> exprMacros = XPath.findAll(tree, "//macro//macro_expr//expr", parser);
                        Collection<ParseTree> declsMacros = XPath.findAll(tree, "//macro//macro_expr//decls//decl", parser);
                        Collection<ParseTree> exprSigFacts = XPath.findAll(tree, "//sigDecl//block_opt//block//expr", parser);


                        //combining all formulas into one list
                        Iterable<ParseTree> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(exprFacts, exprAsserts, exprMacros, declsMacros,
                                        exprSigFacts));

                        Collection<ParseTree> exprTrees = Lists.newArrayList(combinedIterables);


                        //iterating over all formula parse trees
                        for (ParseTree t: exprTrees) {
                            //identifying top-level formulas
                            if (t.getParent() instanceof ALLOYParser.BlockContext ||
                                    t.getParent() instanceof  ALLOYParser.FunExprContext) {
                                formula_count++;
                            }
                        }

                        System.out.println(filePath.toFile());
                        System.out.println("Formula count: " + formula_count);

                        ResultWriter.writeResults(fp_formulaCount, formula_count);
                        tot_formulas += formula_count;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total formula count across all models: " + tot_formulas );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
