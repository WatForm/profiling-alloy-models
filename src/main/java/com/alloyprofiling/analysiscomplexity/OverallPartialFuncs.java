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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OverallPartialFuncs {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting partial functions in Alloy models in " + path);

        //file containing the overall (including utility modules) number of partial formulas per model
        String fp_partFuncs = "Results\\AnalysisComplexity\\overallPartFuncs.txt";

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

                        //XPath path string to extract all occurrences of arrowOp
                        Collection<ParseTree> arrowOpTrees = XPath.findAll(tree, "//arrowOp", parser);

                        //post-processing to pull out partial functions
                        int totPf_count = 0;
                        for(ParseTree t: arrowOpTrees) {
                            int pf_count = 0;
                            String fullDecl = t.getParent().getParent().getText();
                            if(t.getText().equals("->lone") && fullDecl.contains("->lone")){
                                String[] decl_split = fullDecl.split(":");
                                String[] rels_count = decl_split[0].split(",");
                                pf_count += rels_count.length;
                            }
                            totPf_count += pf_count;
                        }

                        //pattern to find total functions of the form sig A {f: one B}
                        ParseTreePattern p_lone = parser.compileParseTreePattern("<names> : lone <name>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches_lone = p_lone.findAll(tree, "//decls/*");

                        ArrayList<String> allPF_lone = new ArrayList<>();

                        //post-processing to pull out all rel names i.e. f1, f2: B counts as 2 total functions
                        List<ParseTree> allNames_lone = matches_lone.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> totalFuncs_lone = allNames_lone.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : totalFuncs_lone) {
                            String[] namesList = names.split(",");
                            Collections.addAll(allPF_lone, namesList);
                        }
                        totPf_count += allPF_lone.size();

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

                        System.out.println("Number of partial functions: " + totPf_count);

                        //writing result file containing the number of partial functions in each file
                        try {
                            FileWriter myWriter = new FileWriter(fp_partFuncs, true);
                            myWriter.write(Integer.toString(totPf_count) + '\n');
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
