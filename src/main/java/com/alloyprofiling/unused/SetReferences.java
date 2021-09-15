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

import java.io.File;
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

//class that computes the average number of set references in formulas in Alloy models
public class SetReferences {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Formulas\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting formula set references in Alloy models in " + path);

        //file containing the FSC of each model
        String fp_setRefs = directoryName + "references.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_setRefs));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterating over repository of models
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

                        //post-processing to pull out open commands without "util"
                        List<ParseTree> modTrees = matches_open.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> userModules = modTrees.stream().map(ParseTree::getText).
                                filter(n-> !(n.contains("util"))).collect(Collectors.toList());

                        //getting signature names from model using SigRetriever
                        List<String> sigNames = SigRetriever.getSigs("all", parser, tree);

                        //getting signature names from opened user modules
                        if (!userModules.isEmpty()) {
                            //skipping files without user-declared modules
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

                        //XPath hierarchy path to find expressions
                        Collection<ParseTree> exprTrees = XPath.findAll(tree, "//expr", parser);

                        //counter for set references
                        int set_count = 0;

                        List<ParseTree> topLevel_exprs = new ArrayList<>();

                        //iterating over expression parse trees
                        for (ParseTree t: exprTrees) {
                            //extracting names in each expression using XPath hierarchy path
                            Collection<ParseTree> nameTrees = XPath.findAll(t, "//name", parser);
                            List<String> name_strings = nameTrees.stream().map(ParseTree::getText).
                                    collect(Collectors.toList());
                            //filtering out expressions that do not have set names in them
                            if (nameTrees.size() != 0 && sigNames.stream().anyMatch(t.getText()::contains)) {
                                //grabbing top-level expressions
                                if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                    topLevel_exprs.add(t);
                                    List<String> sets = name_strings.stream().filter(n -> sigNames.contains(n)).
                                            collect(Collectors.toList());
                                    set_count += sets.size();
                                }
                            }
                        }
                        System.out.println(filePath.toFile());
                        System.out.println("Names count: " + set_count);
                        System.out.println("Top-level exprs: " + topLevel_exprs.size());
                        //not counting models without sets in any expressions
                        //those usually correspond to models split across multiple files
                        //meaning the set names cannot be identified within the same file
                        //not counting models without any expressions --> very simple/incomplete models
                        if (set_count != 0 && !(topLevel_exprs.isEmpty())) {
                            System.out.println("Formula Set References: " + (float) set_count / topLevel_exprs.size());
                            //writing result file containing the number of sets in all expressions of the model
                            try {
                                FileWriter fscWriter = new FileWriter(fp_setRefs, true);
                                fscWriter.write(Float.toString((float) set_count/ topLevel_exprs.size()) + '\n');
                                fscWriter.close();
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
