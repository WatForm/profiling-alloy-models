package com.alloyprofiling.unused;

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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultipleInheritance {
    private static int totMI = 0;
    private static List<String> getAllSigNames(List<String> sigNames) {
        ArrayList<String> resultList = new ArrayList<>();
        for (String names : sigNames) {
            String[] namesList = names.split(",");
            Collections.addAll(resultList, namesList);
        }
        return resultList;
    }
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting uses of multiple inheritance for Alloy models in " + path);

        //file containing the number of occurrences of Multiple Inheritance
        String fp_MI = "Results\\ModelingPractices\\MI.txt";
        //deleting results files if they already exists
        try {
            Files.deleteIfExists(Paths.get(fp_MI));
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

                        //pattern to find children sigs
                        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

                        //getting names of children sigs
                        List<ParseTree> allNames_children = matches_children.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> childrenSigs = allNames_children.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //post-processing to pull out all sig names i.e. sig B, C extends A counts as 2 children sigs
                        List<String> totChildrenSigs = getAllSigNames(childrenSigs);

                        //pattern to find all signature names
                        Collection<ParseTree> sigNameTrees = XPath.findAll(tree, "//sigDecl/names/name", parser);

                        //post-processing to pull out all sig names
                        List<String> allSigs = sigNameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        List<String> allSigNames = getAllSigNames(allSigs);

                        //pattern to find predicates with ID only
                        ParseTreePattern p_ID = parser.compileParseTreePattern("pred <ID> <paraDecls_opt> <block>", ALLOYParser.RULE_predDecl);
                        List<ParseTreeMatch> matches_ID = p_ID.findAll(tree, "//paragraph/*");

                        //pattern to find predicates with name.ID
                        ParseTreePattern p_nameID = parser.compileParseTreePattern("pred <name> . <ID> <paraDecls_opt> <block>", ALLOYParser.RULE_predDecl);
                        List<ParseTreeMatch> matches_nameID = p_nameID.findAll(tree, "//paragraph/*");

                        //post-processing to pull out all predicate IDs
                        List<ParseTree> trees_predIDs = matches_ID.stream().map(match -> match.get("ID")).collect(Collectors.toList());
                        List<String> predIDs = trees_predIDs.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //post-processing to pull out all predicate name.IDs
                        List<ParseTree> trees_predNames = matches_nameID.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<ParseTree> trees_predDotIDs = matches_nameID.stream().map(match -> match.get("ID")).collect(Collectors.toList());

                        List<String> predNames = trees_predNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        List<String> predDotIDs = trees_predDotIDs.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //concatenating the names and the IDs to get name.ID for all predicates that use that form
                        int c = 0;
                        ArrayList<String> predicates = new ArrayList<>();
                        for (String name: predNames) {
                            predicates.add(name + "." + predDotIDs.get(c));
                            predicates.add(predDotIDs.get(c));
                            c++;
                        }

                        predicates.addAll(predIDs);

                        //pattern to find functions with ID only
                        ParseTreePattern f_ID = parser.compileParseTreePattern("<priv> fun <ID> <paraDecls_opt> : <expr> {<expr>}", ALLOYParser.RULE_funDecl);
                        List<ParseTreeMatch> matches_fID = f_ID.findAll(tree, "//paragraph/*");

                        //pattern to find functions  with name.ID
                        ParseTreePattern f_nameID = parser.compileParseTreePattern("<priv> fun <name> . <ID> <paraDecls_opt> : <expr> {<expr>}", ALLOYParser.RULE_funDecl);
                        List<ParseTreeMatch> matches_fnameID = f_nameID.findAll(tree, "//paragraph/*");

                        //post-processing to pull out all functions IDs
                        List<ParseTree> trees_funIDs = matches_fID.stream().map(match -> match.get("ID")).collect(Collectors.toList());
                        List<String> funIDs = trees_funIDs.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //post-processing to pull out all function name.IDs
                        List<ParseTree> trees_funNames = matches_fnameID.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<ParseTree> trees_funDotIDs = matches_fnameID.stream().map(match -> match.get("ID")).collect(Collectors.toList());

                        List<String> funNames = trees_funNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        List<String> funDotIDs = trees_funDotIDs.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //concatenating the names and the IDs to get name.ID for all functions that use that form
                        int index = 0;
                        ArrayList<String> functions = new ArrayList<>();
                        for (String name: funNames) {
                            functions.add(name + "." + funDotIDs.get(index ));
                            functions.add(funDotIDs.get(index));
                            index++;
                        }

                        functions.addAll(funIDs);

                        System.out.println(filePath.toFile());

                        Collection<ParseTree> exprFacts = XPath.findAll(tree, "//factDecl//expr", parser);

                        List<ParseTreeMatch> multiple_inheritance = new ArrayList<>();
                        for (ParseTree t: exprFacts) {
                            if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                for (String sig: totChildrenSigs) {
                                    ParseTreePattern p_mi = parser.compileParseTreePattern(sig + " in <name>", ALLOYParser.RULE_expr);
                                    List<ParseTreeMatch> matches_mi = p_mi.findAll(t, "//*");

                                    List<String> otherSigs = new ArrayList<>(allSigNames);
                                    otherSigs.remove(sig);
                                    List<ParseTreeMatch> temp_mi = new ArrayList<>();
                                    for (ParseTreeMatch m: matches_mi) {
                                        if(otherSigs.stream().anyMatch(m.getTree().getText()::contains) &&
                                        !(functions.stream().anyMatch(m.getTree().getText()::contains)) &&
                                        !(predicates.stream().anyMatch(m.getTree().getText()::contains)))
                                            temp_mi.add(m);
                                    }
                                    if (!temp_mi.isEmpty()) {
                                        multiple_inheritance.addAll(temp_mi);

                                        for (ParseTreeMatch m: temp_mi) {
                                            System.out.println(sig);
                                            System.out.println(m.getTree().getText());
                                        }
                                    }
                                }
                            }
                        }
                        totMI += multiple_inheritance.size();
                        System.out.println("MI: " + multiple_inheritance.size());

                        //writing result file containing occurrences of MI
                        try {
                            FileWriter myWriter = new FileWriter(fp_MI, true);
                            myWriter.write(Integer.toString(multiple_inheritance.size()) + '\n');
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
            System.out.println("Total number of MI: " + totMI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
