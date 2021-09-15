package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
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
import java.util.stream.Stream;

//class that labels formulas in Alloy model according to the style used
public class Formulas {
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
        System.out.println("Counting top-level formulas in Alloy models in " + path);

        String fp_formulaCount = directoryName + "formulaCount.txt";

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

                        //extracting formulas under facts, assertions, functions, predicates, macros and signature facts
                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);
                        Collection<ParseTree> exprAsserts = XPath.findAll(tree, "//assertDecl//expr", parser);
                        Collection<ParseTree> exprFuns = XPath.findAll(tree, "//funDecl//expr", parser);
                        Collection<ParseTree> exprPreds = XPath.findAll(tree, "//predDecl//expr", parser);
                        Collection<ParseTree> exprMacros = XPath.findAll(tree, "//macro//macro_expr//expr", parser);
                        Collection<ParseTree> declsMacros = XPath.findAll(tree, "//macro//macro_expr//decls//decl", parser);
                        Collection<ParseTree> exprSigFacts = XPath.findAll(tree, "//sigDecl//block_opt//block//expr", parser);


                        //combining all formulas into one list
                        Iterable<ParseTree> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(exprFacts, exprAsserts, exprFuns, exprPreds, exprMacros, declsMacros,
                                        exprSigFacts));

                        Collection<ParseTree> exprTrees = Lists.newArrayList(combinedIterables);


                        int formula_count = 0;
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
