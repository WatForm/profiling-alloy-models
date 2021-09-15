package com.alloyprofiling.patternsofuse.formulas;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.FunctionRetriever;
import com.alloyprofiling.retrievers.PredicateRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

//class that labels formulas in Alloy model according to the style used
public class FormulaStyles {
    //counters for each pure/dominant style (across all models)
    private static  int pure_pred = 0;
    private static  int pure_rel = 0;
    private static  int pure_nav = 0;
    private static  int dom_pred = 0;
    private static  int dom_rel = 0;
    private static  int dom_nav = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Formulas\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting uses of formula styles in Alloy models in " + path);

        String fp_predCalc = directoryName + "predCalc.txt";
        String fp_relCalc = directoryName + "relCalc.txt";
        String fp_navExpr = directoryName + "navExpr.txt";

        String fp_domPred = directoryName + "domPred.txt";
        String fp_domRel = directoryName + "domRel.txt";
        String fp_domNav = directoryName + "domNav.txt";

        String fp_purePred = directoryName + "purePred.txt";
        String fp_pureRel = directoryName + "pureRel.txt";
        String fp_pureNav = directoryName + "pureNav.txt";

        //deleting results files if they already exists
        List<String> files = Arrays.asList(fp_predCalc, fp_relCalc, fp_navExpr,
                fp_domPred, fp_domRel, fp_domNav, fp_purePred, fp_pureRel, fp_pureNav);
        try {
            for (String fp: files) {
                Files.deleteIfExists(Paths.get(fp));
            }
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

                        //extracting formulas under facts, assertions, functions, predicates and macros
                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);
                        Collection<ParseTree> exprAsserts = XPath.findAll(tree, "//assertDecl//expr", parser);
                        Collection<ParseTree> exprFuns = XPath.findAll(tree, "//funDecl//expr", parser);
                        Collection<ParseTree> exprPreds = XPath.findAll(tree, "//predDecl//expr", parser);
                        Collection<ParseTree> exprMacros = XPath.findAll(tree, "//macro//macro_expr//expr", parser);
                        Collection<ParseTree> declsMacros = XPath.findAll(tree, "//macro//macro_expr//decls//decl", parser);
                        Collection<ParseTree> exprSigFacts = XPath.findAll(tree, "//sigDecl//block_opt//block//expr", parser);


                        //combining all formulas into one list
                        Iterable<ParseTree> combinedIterables = Iterables.unmodifiableIterable(
                                Iterables.concat(exprFacts, exprAsserts, exprFuns, exprPreds, exprMacros, declsMacros, exprSigFacts));

                        Collection<ParseTree> exprTrees = Lists.newArrayList(combinedIterables);

                        //getting all signature names using SigRetriever
                        List<String> sigNames = SigRetriever.getSigs("all", parser, tree);
                        //getting all predicate names using PredicateRetriever
                        List<String> predicates = PredicateRetriever.getPredicates(parser, tree);
                        //getting all function names using FunctionRetriever
                        List<String> functions = FunctionRetriever.getFunctions(parser, tree);

                        System.out.println(filePath.toFile());

                        //counters for each modeling style
                        int pred_count, rel_count, nav_count;
                        pred_count = rel_count = nav_count = 0;

                        //list of string quantifiers
                        List<String> quantifiers = Arrays.asList("all", "no", "some", "lone", "one", "sum");

                        //iterating over all formula parse trees
                        for (ParseTree t: exprTrees) {
                            //identifying top-level formulas
                            if (t.getParent() instanceof ALLOYParser.BlockContext ||
                                    t.getParent() instanceof  ALLOYParser.FunExprContext) {
                                String expr_str = t.getText();

                                String res_file = "";

                                //classifying formulas

                                //predicate calculus
                                if(!(predicates.stream().anyMatch(expr_str::contains)) && !(functions.stream().
                                        anyMatch(expr_str::contains))) {

                                    if (quantifiers.stream().anyMatch(expr_str::contains) && !(expr_str.contains("."))
                                            && sigNames.stream().anyMatch(expr_str::contains) && expr_str.contains(":")) {
                                        res_file = fp_predCalc;
                                        System.out.println("Pred Calc: " + expr_str);
                                        pred_count++;
                                    }

                                    //navigation expression
                                    else if (quantifiers.stream().anyMatch(expr_str::contains) &&
                                            sigNames.stream().anyMatch(expr_str::contains) && expr_str.contains(":")) {
                                        res_file = fp_navExpr;
                                        System.out.println("Nav Expr: " + expr_str);
                                        nav_count++;
                                    }

                                    //relation calculus
                                    else if(!(expr_str.contains(":"))) {
                                        res_file = fp_relCalc;
                                        System.out.println("Rel Calc: " + expr_str);
                                        rel_count++;
                                    }

                                    //writing to appropriate formula modeling style file
                                    if (!(res_file.isBlank()))
                                        try {
                                            FileWriter myWriter = new FileWriter(res_file, true);
                                            myWriter.write(Integer.toString(1) + '\n');
                                            myWriter.close();
                                        } catch (IOException e) {
                                            System.out.println("An error writing files occurred.");
                                            e.printStackTrace();
                                        }
                                }
                            }
                        }

                        // Initialize map
                        HashMap<String, Integer> counters = new HashMap<String,Integer>();

                        // Inserting a variable
                        counters.put("pred_counter", pred_count);
                        counters.put("rel_counter", rel_count);
                        counters.put("nav_counter", nav_count);

                        boolean pure = false;

                        String fp_pure = "";
                        //checking if model is pure predicate calculus
                        if (rel_count == 0 && nav_count == 0 && pred_count != 0) {
                            fp_pure = fp_purePred;
                            System.out.println("Pure pred");
                            pure = true;
                            pure_pred++;
                        }

                        //checking if model is pure relational calculus
                        else if (pred_count == 0 && nav_count == 0 && rel_count != 0){
                            fp_pure = fp_pureRel;
                            System.out.println("Pure rel");
                            pure = true;
                            pure_rel++;
                        }

                        //checking if model is pure navigation expression
                        else if (pred_count == 0 && rel_count == 0 & nav_count != 0) {
                            fp_pure = fp_pureNav;
                            System.out.println("Pure nav");
                            pure = true;
                            pure_nav++;
                        }

                        //writing to appropriate pure results file if the model is pure
                        if (!(fp_pure.isBlank()))
                            ResultWriter.writeResults(fp_pure, 1);

                        //if model is not pure, write to appropriate dominant results file
                        if (!pure) {

                            // Looking for biggest variable to determine dominant modeling style
                            Map.Entry<String, Integer> maxEntry = null;
                            for (Map.Entry<String, Integer> entry : counters.entrySet())
                            {
                                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                                {
                                    maxEntry = entry;
                                }
                            }

                            String res_model = "";
                            switch (maxEntry.getKey()) {
                                case "pred_counter":
                                    res_model = fp_domPred;
                                    dom_pred++;
                                    System.out.println("Pred Dominant");
                                    break;
                                case "rel_counter":
                                    res_model = fp_domRel;
                                    dom_rel++;
                                    System.out.println("Rel Dominant");
                                    break;
                                case "nav_counter":
                                    res_model = fp_domNav;
                                    System.out.println("Nav Dominant");
                                    dom_nav++;
                                    break;
                            }

                            //writing to appropriate dominant modeling style result file
                            ResultWriter.writeResults(res_model, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total pred dominant models: " + dom_pred);
            System.out.println("Total rel dominant models: " + dom_rel);
            System.out.println("Total nav dominant models: " + dom_nav);
            System.out.println("Total pure pred models: " + pure_pred);
            System.out.println("Total pure rel models: " + pure_rel);
            System.out.println("Total pure nav models: " + pure_nav);
            System.out.println("Total pure models: " + Integer.toString (pure_pred + pure_rel + pure_nav) );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
