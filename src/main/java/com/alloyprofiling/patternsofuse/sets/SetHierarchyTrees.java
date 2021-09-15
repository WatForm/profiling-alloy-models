package com.alloyprofiling.patternsofuse.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that builds the set hierarchy tree of an Alloy model and computes the depth and width of the tree
public class SetHierarchyTrees {

    /** Returns a linked hashmap where the keys are the signature extensions and the values are the
     * parent signatures
     * @param matches   list of ParseTreeMatches containing signature extension declarations
     * @return          a linked hashmap relating extensions to their parent
     */
    public static LinkedHashMap<ParseTree, ParseTree> listToMap(List<ParseTreeMatch> matches, String type) {
        String parent;
        if (type.equalsIgnoreCase("ext"))
            parent = "name";
        else if (type.equalsIgnoreCase("sub"))
            parent = "superSet";
        else
            throw new IllegalStateException("Unexpected value: " + type);
        LinkedHashMap<ParseTree, ParseTree> map = new LinkedHashMap<>();
        for (ParseTreeMatch  m: matches) {
            map.put(m.get("names"), m.get(parent));
        }
        return map;
    }


    /**
     * Adds the extension and parent (if they are not already in the tree) signatures in map to the tree root
     * @param root a tree representing the set hierarchy tree
     * @param map a LinkedHashMap that maps extension signatures to their parent
     */
    private static void addExtNodes(TreeNode<String> root, LinkedHashMap<ParseTree,ParseTree> map) {
        //iterating over map
        for (Map.Entry<ParseTree,ParseTree> entry: map.entrySet()){
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
    }

    /**
     * Adds subset signatures and their superset (if they are not already in the tree) in map to the tree root
     * @param root a tree representing the set hierarchy tree
     * @param map a LinkedHashMap that maps subset signatures to their parent(s)
     */
    private static void addSubNodes (TreeNode<String> root, LinkedHashMap<ParseTree,ParseTree> map) {
        //iterating over map
        for (Map.Entry<ParseTree,ParseTree> entry: map.entrySet()) {
            String children = entry.getKey().getText();
            String parent = entry.getValue().getText();

            if (parent.contains("+")) {

                TreeNode<String> parentNode;

                String[] parents = parent.split("\\+");

                for (String p : parents) {
                    //checking if parent node is already in the tree and parent is a top-level node
                    TreeNode<String> nodeToFind = root.find(p);
                    if (nodeToFind != null) {
                        parentNode = nodeToFind;
                    }
                    else {
                        //checking if parent node is already in the tree and parent is a subset node
                        TreeNode<String> subNodeToFind = root.find("sub:"+p);
                        if (subNodeToFind != null)
                            parentNode = subNodeToFind;
                        else {
                            parentNode = new ArrayMultiTreeNode<>(p);
                            root.add(parentNode);
                        }
                    }

                    //extracting all subset signatures names (can be separated by commas
                    //e.g sig B1, B2 in A1 + A2)
                    String[] namesList = children.split(",");
                    for (String n : namesList) {
                        TreeNode<String> tmpNode = new ArrayMultiTreeNode<>("sub:"+n);
                        //adding subset nodes to parent node
                        parentNode.add(tmpNode);
                    }
                }
            }

            else {
                //checking if parent node is already in the tree and parent is a top-level node
                TreeNode<String> nodeToFind = root.find(parent);

                TreeNode<String> parentNode;
                if (nodeToFind != null) {
                    parentNode = nodeToFind;
                }
                else {
                    //checking if parent node is already in the tree and parent is a subset node
                    TreeNode<String> subNodeToFind = root.find("sub:"+parent);
                    if (subNodeToFind!= null)
                        parentNode = subNodeToFind;
                    else {
                        parentNode = new ArrayMultiTreeNode<>(parent);
                        root.add(parentNode);
                    }
                }

                //extracting all subset signature names (can be separated by commas
                //e.g sig B, C in A)
                String[] namesList = children.split(",");
                for (String n : namesList) {
                    TreeNode<String> tmpNode = new ArrayMultiTreeNode<>("sub:"+n);
                    //adding subset nodes to parent node
                    parentNode.add(tmpNode);
                }
            }
        }
    }
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Generating set hierarchy trees for Alloy models in " + path);

        //file containing the depth of the extension hierarchy tree of each model
        String fp_depth = directoryName + "extDepth.txt";
        //file containing the width of the extension hierarchy tree of each model
        String fp_width = directoryName + "extWidth.txt";
        //file containing the depth of the suset hierarchy tree of each model
        String fp_subDepth = directoryName + "subDepth.txt";

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
                    //calling getHierarchy stats to get a list containing [extDepth, extWidth, subDept] (in that order)
                    List<Integer> stats = getHierarchyStats(filePath);
                    //writing to result files
                    ResultWriter.writeResults(fp_depth, stats.get(0));
                    ResultWriter.writeResults(fp_width, stats.get(1));
                    ResultWriter.writeResults(fp_subDepth, stats.get(2));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getHierarchyStats(Path filePath) {
        List<Integer> stats = new ArrayList<>();
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

            TreeNode<String> root = new ArrayMultiTreeNode<>("TREE_ROOT");

            // post-processing to add signature extensions and parent sigs to hierarchy tree

            LinkedHashMap<ParseTree,ParseTree> extTopMap = listToMap(matches_extensions, "ext");

            //adding extensions to tree
            addExtNodes(root, extTopMap);

            //post-processing to add remaining top-level sigs to hierarchy tree
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
                for (String ext : enum_ext) {
                    TreeNode<String> tmpNode = new ArrayMultiTreeNode<>(ext);
                    parentNode.add(tmpNode);
                }
            }

            //cloning root to keep a version of the set hierarchy that does NOT contain subsets
            //this version of the set hierarchy tree is used to compute the depth and width of
            //extensions
            TreeNode<String> root_copy = SerializationUtils.clone(root);

            //pattern to find signature subsets
            ParseTreePattern p_subsets = parser.compileParseTreePattern(
                    "<priv> <multiplicity> sig <names> in <superSet> { <decls> } <block_opt>",
                    ALLOYParser.RULE_sigDecl);

            List<ParseTreeMatch> matches_subsets= p_subsets.findAll(tree, "//paragraph/*");
            LinkedHashMap<ParseTree,ParseTree> subTopMap = listToMap(matches_subsets, "sub");

            //adding subset signatures to tree
            addSubNodes(root, subTopMap);


            System.out.println(filePath.toFile());

            System.out.println(root_copy);
            //computing depth of extension hierarchy tree
            //the "-1" is for the root node
            int depth = root_copy.height() - 1 ;
            if (depth < 0)
                depth = 0;
            System.out.println("Depth of extension set hierarchy tree: " + depth);
            stats.add(depth);

            int width = 0;

            //computing width of extension set hierarchy tree i.e. counting leaf node (not including top-level
            // sets that are leaf nodes)
            for (TreeNode<String> node : root_copy) {
                if (node.isLeaf() && !(node.level()==1))
                    width++;
            }
            stats.add(width);

            System.out.println("Width of extension set hierarchy tree: " + width);


            List<Integer> subHeights = new ArrayList<>();
            for (TreeNode<String> node : root) {
                if (node.data().contains("sub:")){
                    subHeights.add(node.height());
                }
            }

            System.out.println(root);
            //depth of subset hierarchy tree
            if (!subHeights.isEmpty()) {
                int subDepth = Collections.max(subHeights) + 1; //+ 1 is needed to account for the fact that
                //the 1st sub node in the longest downward path starting at subset node is a subset node
                //itself
                System.out.println("Depth of subset hierarchy tree: " + subDepth);
                stats.add(subDepth);
            }

            else {
                System.out.println("Depth of subset hierarchy tree: 0");
                stats.add(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
