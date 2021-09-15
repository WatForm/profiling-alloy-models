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

public class SetCardinality {
    private static int totSC = 0;
    private static int totSCModels = 0;

    public static int countSetCard(String path) {
        Path filePath = Paths.get(path);
        if (Files.isRegularFile(filePath)) {
            try {
                String source = Files.readString(filePath);

                ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                ParseTree tree = parser.specification();

                int setCard = 0;
                LinkedHashMap<String, Integer> pred_uses_map = PredicateRetriever.countPredicateUses(parser, tree);
                LinkedHashMap<String, Integer> func_uses_map = FunctionRetriever.countFunctionUses(parser, tree);

                Collection<ParseTree> scTrees = XPath.findAll(tree, "//unOp/setCard", parser);
                setCard += scTrees.size();

                Collection<ParseTree> predTrees = XPath.findAll(tree, "//predDecl", parser);

                for (ParseTree t: predTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> pred_setCard = XPath.findAll(t, "//unOp/setCard", parser);
                    try {
                        setCard += pred_uses_map.get(name) * pred_setCard.size();
                    } catch (Exception e) {
                        System.out.println("Predicate not used");
                        setCard += pred_setCard.size();
                    }
                }


                Collection<ParseTree> funcTrees = XPath.findAll(tree, "//funDecl", parser);

                for (ParseTree t: funcTrees) {
                    List<String> names = XPath.findAll(t, "//nameID", parser).stream().map(ParseTree::getText)
                            .collect(Collectors.toList());
                    String name = names.get(0);
                    Collection<ParseTree> func_setCard = XPath.findAll(t, "//unOp/setCard", parser);
                    try {
                        setCard += func_uses_map.get(name) * func_setCard.size();
                    } catch (Exception e) {
                        System.out.println("Function not used");
                        setCard += func_setCard.size();
                    }
                }

                return setCard;

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
        System.out.println("Counting set cardinality uses in Alloy models in " + path);

        //file containing the total number of set cardinality uses per model
        String fp_sc = directoryName + "setCard.txt";


        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_sc));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        int setCard = countSetCard(filePath.toString());
                        System.out.println(filePath.toFile());
                        System.out.println("Uses of set cardinality: " + setCard);
                        totSC += setCard;
                        if (setCard > 0)
                            totSCModels++;

                        //writing result file containing the number of predicates in each file
                        ResultWriter.writeResults(fp_sc, setCard);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of set cardinality uses: " + totSC);
            System.out.println("Total number of models that use set cardinality: " + totSCModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
