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
import java.util.stream.Stream;

public class SetCardinality {
    private static int totSC = 0;
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting set cardinality uses in Alloy models in " + path);

        //file containing the total number of set cardinality uses per model
        String fp_sc = "Results\\AnalysisComplexity\\setCard.txt";

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
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        Collection<ParseTree> scTrees =  XPath.findAll(tree, "//unOp/setCard", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Uses of set cardinality: " + scTrees.size());
                        totSC += scTrees.size();

                        //writing result file containing the number of predicates in each file
                        try {
                            FileWriter myWriter = new FileWriter(fp_sc, true);
                            myWriter.write(Integer.toString(scTrees.size()) + '\n');
                            myWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of set cardinality uses: " + totSC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
