package com.alloyprofiling.analysiscomplexity.partialtotalfuncs;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OverallTotalFuncs {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\AnalysisComplexity\\PartialTotalFunctions\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting total functions in Alloy models in " + path);

        //file containing the overall (including utility modules) number of total functions per model
        String fp_totFuncs = directoryName + "overallTotalFuncs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_totFuncs));

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

                        //post-processing to pull out total functions
                        int totTf_count = 0;
                        for(ParseTree t: arrowOpTrees) {
                            int tf_count = 0;
                            String fullDecl = t.getParent().getParent().getText();
                            if(t.getText().equals("->one") && fullDecl.contains("->one")){
                                String[] decl_split = fullDecl.split(":");
                                String[] rels_count = decl_split[0].split(",");
                                tf_count += rels_count.length;
                            }
                            totTf_count += tf_count;
                        }


                        //pattern to find total functions of the form sig A {f:B}
                        ParseTreePattern p = parser.compileParseTreePattern("<names> : <name>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//decls/*");

                        ArrayList<String> allTF2 = new ArrayList<>();

                        //post-processing to pull out all rel names i.e. f1, f2: B counts as 2 total functions
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> totalFuncs = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : totalFuncs) {
                            String[] namesList = names.split(",");
                            Collections.addAll(allTF2, namesList);
                        }
                        totTf_count += allTF2.size();

                        //pattern to find total functions of the form sig A {f: one B}
                        ParseTreePattern p_one = parser.compileParseTreePattern("<names> : one <name>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches_one = p_one.findAll(tree, "//decls/*");

                        ArrayList<String> allTF_one = new ArrayList<>();

                        //post-processing to pull out all rel names i.e. f1, f2: B counts as 2 total functions
                        List<ParseTree> allNames_one = matches_one.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> totalFuncs_one = allNames_one.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : totalFuncs_one) {
                            String[] namesList = names.split(",");
                            Collections.addAll(allTF_one, namesList);
                        }
                        totTf_count += allTF_one.size();

                        List<String> var_funcs = Arrays.asList("max[", "min[", "first[", "last[");
                        List<String> var_funcs_noArg = Arrays.asList("max", "min", "first", "last");


                        Collection<ParseTree> exprs = XPath.findAll(tree, "//expr", parser);

                        List<String> used_tf = new ArrayList<>();
                        for (ParseTree t: exprs) {
                            if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                if (!var_funcs.stream().anyMatch(t.getText()::contains)
                                        && var_funcs_noArg.stream().anyMatch(t.getText()::contains)) {
                                    for (String func: var_funcs_noArg){
                                        int tmp_count = StringUtils.countMatches(t.getText(), func);
                                        if (tmp_count > 0)
                                            used_tf.add(func);
                                    }
                                }
                            }
                        }

                        totTf_count+= used_tf.stream().distinct().collect(Collectors.toList()).size();


                        System.out.println(filePath.toFile());

                        System.out.println("Number of total functions: " + totTf_count);

                        //writing result file containing the number of total functions in each file
                        ResultWriter.writeResults(fp_totFuncs, totTf_count);

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
