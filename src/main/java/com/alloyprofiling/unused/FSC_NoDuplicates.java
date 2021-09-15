package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// SCRIPT NOT USED
// FSC with duplicates was deemed more suitable
public class FSC_NoDuplicates {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing FSC for Alloy models in " + path);

        //deleting results file if it already exists
        String fp_FSC = "Results\\QualityIndicators\\FSC_NoDup.txt";
        try {
            Files.deleteIfExists(Paths.get(fp_FSC));
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

                        //pattern to find open commands
                        ParseTreePattern p_open = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches_open = p_open.findAll(tree, "//specification/*");


                        //post-processing to pull out open commands without util"
                        List<ParseTree> modTrees = matches_open.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> userModules = modTrees.stream().map(ParseTree::getText).
                                filter(n-> !(n.contains("util"))).collect(Collectors.toList());

                        //getting signature names from model
                        List<String> sigNames = SigRetriever.getSigs("all", parser, tree);

                        //getting signature names from opened user modules
                        if (!userModules.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            for (String module: userModules) {
                                try {
                                    String module_str = Files.readString(Path.of(path + "\\" + module + ".als"));
                                    ALLOYLexer lexer_mod = new ALLOYLexer(CharStreams.fromString(module_str));
                                    ALLOYParser parser_mod = new ALLOYParser(new CommonTokenStream(lexer_mod));
                                    ParseTree tree_mod = parser_mod.specification();
                                    sigNames.addAll(SigRetriever.getSigs("all", parser_mod,tree_mod));
                                } catch (IOException e) {
                                    //skipping files where imported user-module cannot be located
                                    System.out.println("Skipped: " + filePath.toFile());
                                    return;
                                } catch (RecognitionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        //pattern to find expressions
                        Collection<ParseTree> exprTrees = XPath.findAll(tree, "//expr", parser);

                        int name_count = 0;

                        List<ParseTree> topLevel_exprs = new ArrayList<>();


                        for (ParseTree t: exprTrees) {
                            Collection<ParseTree> nameTrees = XPath.findAll(t, "//name", parser);
                            List<String> name_strings = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                            //filtering out expressions that do not have set names in them
                            if (nameTrees.size() != 0 && sigNames.stream().anyMatch(t.getText()::contains)) {
                                //grabbing top-level expressions
                                if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                    topLevel_exprs.add(t);
                                    List<String> sets = name_strings.stream().filter(n -> sigNames.contains(n)).
                                            distinct().collect(Collectors.toList());
                                    name_count += sets.size();
                                }
                            }
                        }
                        System.out.println(filePath.toFile());
                        System.out.println("Names count: " + name_count);
                        System.out.println("Top-level exprs: " + topLevel_exprs.size());
                        //not counting models without sets in any expressions
                        //those usually correspond to models split across multiple files
                        //meaning the set names cannot be identified within the same file
                        //not counting models without any expressions --> very simple/incomplete models
                        if (name_count != 0 && !(topLevel_exprs.isEmpty())) {
                            System.out.println("FSC: " + (float) name_count / topLevel_exprs.size());
                            //writing result file containing the number of sets in all expressions of the model
                            try {
                                FileWriter myWriter = new FileWriter(fp_FSC, true);
                                myWriter.write(Float.toString((float) name_count/ topLevel_exprs.size()) + '\n');
                                myWriter.close();
                            } catch (IOException e) {
                                System.out.println("An error writing files occurred.");
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
