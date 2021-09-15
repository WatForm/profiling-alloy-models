package com.alloyprofiling.analysiscomplexity.secondorderoperators;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.FunctionRetriever;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TCOperators {
    private static int totTC = 0;
    private static int totNonRefTC = 0;
    private static int totRefTC = 0;
    private static int totTCModels = 0;
    private static int totRefTCModels = 0;
    private static int totNonRefTCModels = 0;
    private static int totBothOps = 0;

    public static int countTC(String path) {
        Path filePath = Paths.get(path);
        if (Files.isRegularFile(filePath)) {
            try {
                String source = Files.readString(filePath);

                ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                ParseTree tree = parser.specification();

                int transClosure = 0;
                LinkedHashMap<String, Integer> pred_uses_map = PredicateRetriever.countPredicateUses(parser, tree);
                LinkedHashMap<String, Integer> func_uses_map = FunctionRetriever.countFunctionUses(parser, tree);

                Collection<ParseTree> tcTrees =  XPath.findAll(tree, "//unOp/tcOp", parser);
                transClosure += tcTrees.size();

                Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                for (ParseTree t: predTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> pred_transClosure = XPath.findAll(t, "//unOp/tcOp", parser);
                    try {
                        transClosure += pred_uses_map.get(name) * pred_transClosure.size();
                    } catch (Exception e) {
                        System.out.println("Predicate not used");
                        transClosure += pred_transClosure.size();
                    }
                }


                Collection<ParseTree> funcTrees = XPath.findAll(tree, "//funDecl", parser);

                for (ParseTree t: funcTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> func_transClosure = XPath.findAll(t, "//unOp/tcOp", parser);
                    try {
                        transClosure += func_uses_map.get(name) * func_transClosure.size();
                    } catch (Exception e) {
                        System.out.println("Function not used");
                        transClosure += func_transClosure.size();
                    }
                }

                return transClosure;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    public static int countTC_kind(String path, String kind) {
        Path filePath = Paths.get(path);
        if (Files.isRegularFile(filePath)) {
            try {
                String operator;
                if (kind.equals("^"))
                    operator = "^";
                else if (kind.equals("*"))
                    operator = "*";
                else
                    throw new IllegalStateException("Unexpected value: " + kind);

                String source = Files.readString(filePath);

                ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                ParseTree tree = parser.specification();

                int transClosure = 0;
                LinkedHashMap<String, Integer> pred_uses_map = PredicateRetriever.countPredicateUses(parser, tree);
                LinkedHashMap<String, Integer> func_uses_map = FunctionRetriever.countFunctionUses(parser, tree);

                Collection<ParseTree> tcTrees =  XPath.findAll(tree, "//unOp/tcOp", parser);

                List<String> tc_kind = tcTrees.stream().map(ParseTree::getText).filter(o -> o.equals(operator))
                        .collect(Collectors.toList());
                transClosure += tc_kind.size();

                Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                for (ParseTree t: predTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> pred_transClosure = XPath.findAll(t, "//unOp/tcOp", parser);
                    List<String> pred_tc_kind = pred_transClosure.stream().map(ParseTree::getText).filter(o -> o.equals(operator))
                            .collect(Collectors.toList());
                    try {
                        transClosure += pred_uses_map.get(name) * pred_tc_kind.size();
                    } catch (Exception e) {
                        System.out.println("Predicate not used");
                        transClosure += pred_tc_kind.size();
                    }
                }


                Collection<ParseTree> funcTrees = XPath.findAll(tree, "//funDecl", parser);

                for (ParseTree t: funcTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> func_transClosure = XPath.findAll(t, "//unOp/tcOp", parser);
                    List<String> func_tc_kind = func_transClosure.stream().map(ParseTree::getText).filter(o -> o.equals(operator))
                            .collect(Collectors.toList());
                    try {
                        transClosure += func_uses_map.get(name) * func_tc_kind.size();
                    } catch (Exception e) {
                        System.out.println("Function not used");
                        transClosure += func_tc_kind.size();
                    }
                }

                return transClosure;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\AnalysisComplexity\\SecondOrderOperators\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting TC operators in Alloy models in " + path);

        String fp_tcOp = directoryName + "tcOp.txt";
        //file containing the number of reflexive transitive closure operators per model
        String fp_ref = directoryName + "reftc.txt";
        //file containing the number of non-reflexive (positive) transitive closure operators per model
        String fp_non_ref = directoryName + "nonreftc.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_tcOp));
            Files.deleteIfExists(Paths.get(fp_ref));
            Files.deleteIfExists(Paths.get(fp_non_ref));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        int tcOp = countTC(filePath.toString());
                        System.out.println(filePath.toFile());
                        System.out.println("Uses of transitive closure: " + tcOp);
                        totTC += tcOp;
                        if (tcOp > 0)
                            totTCModels++;

                        //writing result file containing the number of transitive closure operators in each file
                        ResultWriter.writeResults(fp_tcOp, tcOp);

                        int tcOp_nonref = countTC_kind(filePath.toString(), "^");
                        System.out.println(filePath.toFile());
                        System.out.println("Uses of non-reflexive transitive closure: " + tcOp_nonref);
                        totNonRefTC += tcOp_nonref;
                        if (tcOp_nonref > 0)
                            totNonRefTCModels++;

                        //writing result file containing the number of predicates in each file
                        ResultWriter.writeResults(fp_non_ref, tcOp_nonref);

                        int tcOp_ref = countTC_kind(filePath.toString(), "*");
                        System.out.println(filePath.toFile());
                        System.out.println("Uses of reflexive transitive closure: " + tcOp_ref);
                        totRefTC += tcOp_ref;
                        if (tcOp_ref > 0)
                            totRefTCModels++;

                        //writing result file containing the number of predicates in each file
                        ResultWriter.writeResults(fp_ref, tcOp_ref);

                        if (tcOp_ref > 0 && tcOp_nonref > 0)
                            totBothOps++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of transitive closure operator uses: " + totTC);
            System.out.println("Total number of non-reflexive transitive closure operator uses: " + totNonRefTC);
            System.out.println("Total number of reflexive transitive closure operator uses: " + totRefTC);
            System.out.println("Total number of models that use transitive closure operator: " + totTCModels);
            System.out.println("Total number of models that use non-reflexive transitive closure operator: " + totNonRefTCModels);
            System.out.println("Total number of models that use reflexive transitive closure operator: " + totRefTCModels);
            System.out.println("Models that use both operators: " + totBothOps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
