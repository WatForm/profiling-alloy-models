package com.alloyprofiling.patternsofuse.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DIT_WIT {
    /** Returns a linked hashmap where the keys are the signature extensions and the values are the
     * parent signatures
     * @param matches   list of ParseTreeMatches containing signature extension declarations
     * @return          a linked hashmap relating extensions to their parent
     */
    public static LinkedHashMap<ParseTree, ParseTree> listToMap(List<ParseTreeMatch> matches) {
        LinkedHashMap<ParseTree, ParseTree> map = new LinkedHashMap<>();
        for (ParseTreeMatch  m: matches) {
            map.put(m.get("names"), m.get("name"));
        }
        return map;
    }

    public static void main(String[] args) {
        //repository of models
        String path = "alloy_models";
        System.out.println("Generating DIT for Alloy models in " + path);

        //file containing the DIT of each model
        String fp_DIT = "Results\\QualityIndicators\\DIT.txt";
        //file containing the WIT of each model
        String fp_WIT = "Results\\QualityIndicators\\WIT.txt";


        //deleting results files if they already exists
        try {
            Files.deleteIfExists(Paths.get(fp_DIT));
            Files.deleteIfExists(Paths.get(fp_WIT));
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

                        //pattern to find signature extensions
                        ParseTreePattern p_extensions = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_extensions= p_extensions.findAll(tree, "//paragraph/*");

                        //pattern to find top-level sigs (i.e. without "extends")
                        ParseTreePattern topLevelSigs = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_topSigs = topLevelSigs.findAll(tree, "//paragraph/*");

                        System.out.println(matches_topSigs.size());
                        TreeNode<String> root = new ArrayMultiTreeNode<>("DIT_ROOT");

                        // post-processing to add signature extensions and parent sigs to inheritance tree

                        LinkedHashMap<ParseTree,ParseTree> extTopMap = listToMap(matches_extensions);

                        //iterating over map
                        for (Map.Entry<ParseTree,ParseTree> entry: extTopMap.entrySet()){
                            String children = entry.getKey().getText();
                            String parent = entry.getValue().getText();

                            //checking if parent node is already in the tree
                            TreeNode<String> nodeToFind = root.find(parent);

                            TreeNode<String> parentNode;
                            if (nodeToFind != null) {
                                parentNode = nodeToFind;
                            }
                            else {
                                parentNode = new ArrayMultiTreeNode<>(parent);
                                root.add(parentNode);
                            }

                            //extracting all signature extension names (can be separated by commas
                            //e.g sig B, C extends A)
                            String[] namesList = children.split(",");
                            for (String n : namesList) {
                                TreeNode<String> tmpNode = new ArrayMultiTreeNode<>(n);
                                //adding extension nodes to parent node
                                parentNode.add(tmpNode);
                            }
                        }

                        //post-processing to add remaining top-level sigs to inheritance tree
                        List<ParseTree> topSigs_trees = matches_topSigs.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> topSigNames = topSigs_trees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : topSigNames) {
                            String[] namesList = names.split(",");
                            for (String n : namesList) {
                                TreeNode<String> nodeToFind = root.find(n);
                                if (nodeToFind == null) {
                                    TreeNode<String> newNode = new ArrayMultiTreeNode<>(n);
                                    root.add(newNode);
                                }
                            }
                        }

                        //map that relates top-level enums with their extensions
                        //e.g. enum Time [morning, noon, night] --> enumMap.get("Time") returns [morning, noon, night]
                        LinkedHashMap<String, List<String>> enumMap = EnumRetriever.getEnumMap(parser, tree);
                        //iterating over map
                        for (Map.Entry<String,List<String>> entry: enumMap.entrySet()) {
                            String topSig = entry.getKey();
                            TreeNode<String> parentNode = new ArrayMultiTreeNode<>(topSig);
                            //adding parent node to tree
                            root.add(parentNode);
                            List<String> enum_ext = entry.getValue();
                            //adding extension nodes to parent node
                            for (String e : enum_ext) {
                                TreeNode<String> tmpNode = new ArrayMultiTreeNode<>(e);
                                parentNode.add(tmpNode);
                            }
                        }

                        System.out.println(filePath.toFile());

                        //computing depth of inheritance tree
                        //the "-1" is for the root node
                        int DIT = root.height() -1 ;
                        if (DIT < 0)
                            DIT = 0;
                        System.out.println("DIT: " + DIT);

                        int width = 0;
                        //computing width of inheritance tree i.e. counting leaf node
                        for (TreeNode<String> node : root) {
                            if (node.isLeaf())
                                width++;
                        }

                        System.out.println("WIT: " + width);

                        try {
                            //writing to DIT result file
                            FileWriter DITWriter = new FileWriter(fp_DIT, true);
                            DITWriter.write(Integer.toString(DIT) + '\n');
                            DITWriter.close();

                            //writing to WIT result file
                            FileWriter WITWriter = new FileWriter(fp_WIT, true);
                            WITWriter.write(Integer.toString(width) + '\n');
                            WITWriter.close();

                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
