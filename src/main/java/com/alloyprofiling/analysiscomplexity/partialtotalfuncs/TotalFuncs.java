package com.alloyprofiling.analysiscomplexity.partialtotalfuncs;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TotalFuncs {
    private static int totTotalTopLevel = 0;
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

        //file containing the number of user-created total functions per model
        String fp_totFuncs = directoryName + "totalFuncs.txt";

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

                        List<String> topSigs = SigRetriever.getSigs("top", parser,tree);

                        //XPath path string to extract all occurrences of arrowOp
                        Collection<ParseTree> arrowOpTrees = XPath.findAll(tree, "//arrowOp", parser);

                        //post-processing to pull out total functions
                        int totTf_count = 0;
                        for(ParseTree t: arrowOpTrees) {
                            int tf_count = 0;
                            String fullDecl = t.getParent().getParent().getText();
                            String[] arrowSplit = fullDecl.split("->");
                            //if(t.getText().equals("->one") && fullDecl.contains("->one")) //old if
                            if(t.getText().equals("->one") && fullDecl.contains("->one") &&
                                    arrowSplit[arrowSplit.length - 1].contains("one")){
                                String[] decl_split = fullDecl.split(":");
                                String[] rels_count = decl_split[0].split(",");
                                List<String> decl_sets = Arrays.stream(arrowSplit).map(sig -> sig.substring(sig.indexOf(":")+1)).
                                        map(sig -> sig.replaceAll("one|lone|some|set", "").trim()).
                                        collect(Collectors.toList());
                                if (decl_sets.stream().allMatch(topSigs::contains)) {
                                    totTotalTopLevel += rels_count.length;
                                }
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

                        for (ParseTreeMatch m: matches) {
                            String range = m.get("name").getText();
                            String names = m.get("names").getText();
                            String[] namesList = names.split(",");
                            if (topSigs.contains(range))
                                totTotalTopLevel+= namesList.length;
                        }

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

                        for (ParseTreeMatch m: matches_one) {
                            String range = m.get("name").getText();
                            String names = m.get("names").getText();
                            String[] namesList = names.split(",");
                            if (topSigs.contains(range))
                                totTotalTopLevel+= namesList.length;
                        }


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
        System.out.println("Total number of total functions with top-level signatures for range: " + totTotalTopLevel);
    }
}
