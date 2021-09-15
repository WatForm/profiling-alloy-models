package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that builds the subset set hierarchy tree of an Alloy model and computes the depth and width of the tree
public class SubsetSetHierarchy {

    /** Returns a linked hashmap where the keys are the signature subsets and the values are the
     * parent signatures
     * @param matches   list of ParseTreeMatches containing subset signature declarations
     * @return          a linked hashmap relating subsets to their parent
     */
    public static LinkedHashMap<ParseTree, ParseTree> listToMap(List<ParseTreeMatch> matches) {
        LinkedHashMap<ParseTree, ParseTree> map = new LinkedHashMap<>();
        for (ParseTreeMatch  m: matches) {
            map.put(m.get("names"), m.get("superSet"));
        }
        return map;
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Signatures\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "C:\\Users\\eeid\\Desktop\\subModels";
        System.out.println("Generating subset set hierarchy trees for Alloy models in " + path);

        //file containing the depth of the hierarchy tree of each model
        String fp_depth = directoryName + "subHeight.txt";
        //file containing the width of the hierarchy tree of each model
        String fp_width = directoryName + "subWidth.txt";


        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_depth));
            Files.deleteIfExists(Paths.get(fp_width));
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

                        //pattern to find signature subsets
                        ParseTreePattern p_subsets = parser.compileParseTreePattern(
                                "<priv> <multiplicity> sig <names> in <superSet> { <decls> } <block_opt>",
                                ALLOYParser.RULE_sigDecl);

                        List<ParseTreeMatch> matches_subsets= p_subsets.findAll(tree, "//paragraph/*");

                        //pattern to find top-level sigs (i.e. without "extends")
                        ParseTreePattern topLevelSigs = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_topSigs = topLevelSigs.findAll(tree, "//paragraph/*");

                        TreeNode<String> root = new ArrayMultiTreeNode<>("TREE_ROOT");

                        // post-processing to add signature subsets and parent sigs to hierarchy tree

                        LinkedHashMap<ParseTree,ParseTree> subTopMap = listToMap(matches_subsets);

                        //iterating over map
                        for (Map.Entry<ParseTree,ParseTree> entry: subTopMap.entrySet()){
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

                            //extracting all subset names (can be separated by commas
                            //e.g sig B, C in A)
                            String[] namesList = children.split(",");
                            for (String n : namesList) {
                                TreeNode<String> tmpNode = new ArrayMultiTreeNode<>(n);
                                //adding extension nodes to parent node
                                parentNode.add(tmpNode);
                            }
                        }

                        //post-processing to add remaining top-level sigs to subset hierarchy tree
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

                        System.out.println(filePath.toFile());

                        //computing depth of subset hierarchy tree
                        //the "-1" is for the root node
                        int depth = root.height() -1 ;
                        if (depth < 0)
                            depth = 0;
                        System.out.println("Depth of subset set hierarchy tree: " + depth);

                        int width = 0;

                        //computing width of hierarchy tree i.e. counting leaf node (not including top-level sets that
                        //are leaf nodes of the root)
                        for (TreeNode<String> node : root) {
                            if (node.isLeaf() && !(node.level()==1))
                                width++;
                        }

                        System.out.println("Width of subset set hierarchy tree: " + width);

                        System.out.println(root);

                        ResultWriter.writeResults(fp_depth, depth);
                        ResultWriter.writeResults(fp_width, width);

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
