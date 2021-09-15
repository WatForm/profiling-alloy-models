package com.alloyprofiling.characteristics.linear;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.characteristics.length.ModelLength;
import com.alloyprofiling.retrievers.FunctionRetriever;
import com.alloyprofiling.retrievers.PredicateRetriever;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;
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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Computes the length (not including blank lines) of Alloy models in a given repository
public class LengthSetsFormulas {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Linear\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting non-empty lines, number of sets and number of formulas for Alloy models in  " + path);

        //file containing the number of lines in each model
        String fp_lengthSetsExprs = directoryName + "lengthSetsExprs.csv";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_lengthSetsExprs));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(fp_lengthSetsExprs);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"length", "sets", "exprs"};
            writer.writeNext(header);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating csv file");
        }

        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        //counting non-empty lines in alloy model
                        long length = ModelLength.getLength(String.valueOf(filePath));

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

                        //extracting formulas under facts, assertions, functions, predicates and macros
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

                        for (ParseTree t: exprTrees) {
                            //identifying top-level formulas
                            if (t.getParent() instanceof ALLOYParser.BlockContext ||
                                    t.getParent() instanceof  ALLOYParser.FunExprContext) {
                                formula_count++;
                            }
                        }

                        List<String> signatures = SigRetriever.getSigs("all", parser, tree);
                        List<String> fields = RelationRetriever.getRelations(parser, tree);
                        int sets = signatures.size() + fields.size();

                        if (length > 0 && formula_count > 0 & sets > 0) {

                            System.out.println(filePath.toFile());
                            System.out.println("Model Length: " + length);
                            System.out.println("Formulas: " + formula_count);
                            System.out.println("Sets: " + sets);

                            try {
                                // create FileWriter object with file as parameter
                                FileWriter outputfile = new FileWriter(file, true);

                                // create CSVWriter object filewriter object as parameter
                                CSVWriter writer = new CSVWriter(outputfile);

                                // add data to csv
                                String[] values = {Long.toString(length), Integer.toString(sets),
                                        Integer.toString(formula_count)};
                                writer.writeNext(values);

                                // closing writer connection
                                writer.close();
                            }  catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
