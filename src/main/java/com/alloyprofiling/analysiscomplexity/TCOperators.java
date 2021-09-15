package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TCOperators {
    private static int totTC = 0;
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting TC operators in Alloy models in " + path);

        //file containing the number of reflexive transitive closure operators per model
        String fp_ref = "Results\\AnalysisComplexity\\refTC.txt";
        //file containing the number of non-reflexive (positive) transitive closure operators per model
        String fp_non_ref = "Results\\AnalysisComplexity\\nonRefTC.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_ref));
            Files.deleteIfExists(Paths.get(fp_non_ref));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        Collection<ParseTree> tcTrees =  XPath.findAll(tree, "//unOp/tcOp", parser);

                        List<String> ref_tc = tcTrees.stream().map(ParseTree::getText).filter(o -> o.equals("*"))
                                .collect(Collectors.toList());

                        List<String> non_ref_tc = tcTrees.stream().map(ParseTree::getText).filter(o -> o.equals("^"))
                                .collect(Collectors.toList());

                        System.out.println(filePath.toFile());
                        System.out.println("Uses of reflexive transitive closure: " + ref_tc.size());
                        System.out.println("Uses of non-reflexive transitive closure: " + non_ref_tc.size());
                        totTC += tcTrees.size();

                        //writing result file containing the number of predicates in each file
                        try {
                            FileWriter refWriter = new FileWriter(fp_ref, true);
                            refWriter.write(Integer.toString(ref_tc.size()) + '\n');
                            refWriter.close();

                            FileWriter nonRefWriter = new FileWriter(fp_non_ref, true);
                            nonRefWriter.write(Integer.toString(non_ref_tc.size()) + '\n');
                            nonRefWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of transitive closure operator uses: " + totTC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
