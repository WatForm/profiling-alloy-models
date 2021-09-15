package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartialFuncs_util {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting util partial functions models in " + path);

        //file containing the number of partial functions from the util modules per model
        String fp_partFuncs = "Results\\AnalysisComplexity\\utilPartFuncs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_partFuncs));

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

                        int totPf_count = 0;

                        List<String> partial_funcs = Arrays.asList("isSeq", "seqElems", "int2elem",
                                "elem2int", "inc", "dec", "add","sub","mul", "div", "lastIdx", "afteLastIdx",
                                "idxOf", "lastIdxOf", "at");

                        List<String> var_funcs = Arrays.asList("max", "min", "first", "last");

                        List<String> names = XPath.findAll(tree, "//expr//name", parser).
                                stream().map(ParseTree::getText).collect(Collectors.toList());

                        List<String> pf =  names.stream().filter(n -> partial_funcs.stream().anyMatch(n::equals)).
                                distinct().collect(Collectors.toList());

                        totPf_count += pf.size();


                        for (String f: var_funcs) {
                            List<String> used_pf = new ArrayList<>();
                            ParseTreePattern p = parser.compileParseTreePattern(f + " [<expr>]", ALLOYParser.RULE_expr);
                            List<ParseTreeMatch> matches = p.findAll(tree, "//*");
                            if (!matches.isEmpty())
                                totPf_count++;
                        }


                        System.out.println(filePath.toFile());
                        //System.out.println(totPf_count);
                        //System.out.println(partFuncs);

                        System.out.println("Number of util partial functions: " + totPf_count);

                        try {
                            FileWriter upfWrite = new FileWriter(fp_partFuncs, true);
                            upfWrite.write(Integer.toString(totPf_count) + '\n');
                            upfWrite.close();
                        } catch (Exception e) {
                            e.printStackTrace();
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
