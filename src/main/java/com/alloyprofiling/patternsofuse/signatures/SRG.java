package com.alloyprofiling.patternsofuse.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SRG {

    public static LinkedHashMap<ParseTree, ParseTree> listToMap(List<ParseTreeMatch> matches, String key, String value) {
        LinkedHashMap<ParseTree, ParseTree> map = new LinkedHashMap<>();
        for (ParseTreeMatch  m: matches) {
            map.put(m.get(key), m.get(value));
        }
        return map;
    }

    private static List<String> getSigNames(ParseTreeMatch match) {
        //post-processing to pull out all sig names e.g. <mult> sig A, B { } counts as 2 sigs
        List<String> sigNames = new ArrayList<>();
        String[] namesList = match.get("names").getText().split(",");
        Collections.addAll(sigNames, namesList);
        return  sigNames;
    }

    private static void addSigs(Graph<String, DefaultEdge> scg,  ALLOYParser parser) {
        //pattern to find extension sigs
        ParseTree tree = parser.specification();
        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

        // post-processing to add extensions and parent sigs to SRG
        Map<ParseTree,ParseTree> cpMap = listToMap(matches_children, "names", "name");

        for (Map.Entry<ParseTree,ParseTree> entry: cpMap.entrySet()){
            String children = entry.getKey().getText();
            String parent = entry.getValue().getText();

            // adding parent to SRG
            if (!(scg.containsVertex(parent)))
                scg.addVertex(parent);

            // adding extensions to SRG
            String[] namesList = children.split(",");
            for (String n : namesList) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
                // adding edges between parent and children
                //System.out.println(parent + " " +  n);
                createEdge(scg, parent, n);
            }
        }

        //pattern to find subset sigs
        ParseTreePattern p_subsets = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> in <superSet> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_subsets = p_subsets.findAll(tree, "//paragraph/*");

        // post-processing to add subsets and parent sigs to SRG
        Map<ParseTree,ParseTree> inMap = listToMap(matches_subsets, "superSet", "names");

        //post-processing to add subset sigs and superset sigs to SRG
        for (Map.Entry<ParseTree,ParseTree> entry: inMap.entrySet()){
            String superset = entry.getKey().getText();
            String subsets = entry.getValue().getText();

            // adding superset(s) to SRG
            String[] supersetList = superset.split("\\+");
            for (String n : supersetList) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
            }

            // adding subset(s) to SRG
            String[] subsetList = subsets.split(",");
            for (String n : subsetList) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
                // adding edges between parent and children
                //System.out.println(superset + " " +  n);
            }

            for (String sub: subsetList) {
                for (String sup: supersetList) {
                    createEdge(scg, sub, sup);
                }
            }
        }

        //pattern to find remaining top-level sigs
        ParseTreePattern remSigs = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_remSigs = remSigs.findAll(tree, "//paragraph/*");



        //post-processing to add remaining sigs to SRG
        List<ParseTree> allNames = matches_remSigs.stream().map(match -> match.get("names")).collect(Collectors.toList());
        List<String> remSigsNames = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
        for (String names : remSigsNames) {
            String[] sigNames = names.split(",");
            for (String n : sigNames) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
            }
        }

        //getting map of enums
        LinkedHashMap<String, List<String>> enumMap = EnumRetriever.getEnumMap(parser, tree);
        for (Map.Entry<String,List<String>> entry: enumMap.entrySet()) {
            String parent = entry.getKey();
            List<String> enum_exts = entry.getValue();

            // adding parent to SRG
            if (!(scg.containsVertex(parent)))
                scg.addVertex(parent);

            // adding extensions to SRG
            for (String n : enum_exts) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
                // adding edges between parent and children
                //System.out.println(parent + " " +  n);
                createEdge(scg, parent, n);
            }
        }

        //pattern to find expressions in signature field declarations

        ParseTreePattern p_sigDecls = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_sigDecls = p_sigDecls.findAll(tree, "//paragraph/*");

        // list containing built-in Alloy types
        List<String> builtInTypes = Arrays.asList("String", "Int", "seq/Int", "univ", "set");
        //post-processing
        for (ParseTreeMatch match: matches_sigDecls) {
            ArrayList<String> sigDecls = new ArrayList<>();
            String names = match.get("names").getText();
            String[] sigNames = names.split(",");
            Collections.addAll(sigDecls, sigNames);
            ParseTree decls_tree = match.get("decls");
            if (!(decls_tree.getText().isBlank())) {
                List<String> name_decls =  XPath.findAll(decls_tree, "//expr//name", parser).stream().map(t -> t.getText()).collect(Collectors.toList());
                List<String> names_noDup = new ArrayList<>(
                        new HashSet<>(name_decls));

                for (String name: names_noDup) {
                    if (!(scg.containsVertex(name)) && !(builtInTypes.contains(name)))
                        scg.addVertex(name);
                }

                for (String sig: sigDecls) {
                    if (!scg.containsVertex(sig))
                        scg.addVertex(sig);
                }

                for (String name: names_noDup) {
                    for (String sig: sigDecls ) {
                        //System.out.println(name);
                        if (!(builtInTypes.contains(name)) && !(name.equals(sig)) )
                            createEdge(scg, name, sig);
                    }
                }
            }
        }

        //getting all signature declaration packages
        ParseTreePattern p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");

        //getting relations (with map) and sets
        LinkedHashMap<String, List<String>> relMap = RelationRetriever.getRelSigMap(parser, tree);
        List<String> relations = new ArrayList<>(relMap.keySet());
        List<String> sets = SigRetriever.getSigs("all", parser, tree);

        LinkedHashMap<String, List<String>> coupledSigs = new LinkedHashMap<>();
        LinkedHashMap<List<String>, List<String>> coupledRelations = new LinkedHashMap<>();
        for (ParseTreeMatch m: matches) {
            ParseTree block = m.get("block");
            if (!StringUtils.strip( block.getText(), "{}").isBlank()) {
                List<String> sigs = getSigNames(m);
                List<String> otherRelations = new ArrayList<>(relations);
                //removing each signature's relations to get relations that belong to other signatures
                List<String> mRelations = XPath.findAll(m.getTree(), "//decls//decl", parser).
                        stream().map(ParseTree::getText).collect(Collectors.toList());
                List<String> mRelNames = new ArrayList<>();
                mRelations.stream().forEach(r -> mRelNames.addAll(Arrays.asList(r.split(":")[0].
                        split(","))));
                otherRelations.removeAll(mRelNames);
                List<String> otherSets = new ArrayList<>(sets);
                //removing each signature's name to get the names of other signatures
                otherSets.removeAll(getSigNames(m));

                List<String> block_sets = XPath.findAll(block, "//expr//name", parser).stream().
                        filter(n -> sets.contains(n.getText()) || relations.contains(n.getText())).
                        map(ParseTree::getText).collect(Collectors.toList());

                otherRelations.forEach(r -> {
                    if (block_sets.contains(r))
                        coupledRelations.put(relMap.get(r), sigs);
                });

                otherSets.forEach(s -> {
                    if (block_sets.contains(s))
                        coupledSigs.put(s, sigs);
                });

                //coupledRelations.forEach((key, value) -> System.out.println(key + ":" + value));

                coupledRelations.forEach((key, value) -> key.forEach( s1 -> value.forEach(s2 -> createEdge(scg, s1, s2))));

               //coupledSigs.forEach((key, value) -> System.out.println(key + ":" + value));

                coupledSigs.keySet().forEach(key -> {
                    if (!scg.containsVertex(key))
                        scg.addVertex(key);
                });
                coupledSigs.forEach((key, value) -> value.forEach(s2 -> createEdge(scg, key, s2)));
            }
        }
    }

    private static Graph<String,DefaultEdge> createEdge(Graph<String, DefaultEdge> g, String v1, String v2) {
        g.addEdge(v1, v2);
        g.addEdge(v2, v1);
        return g;
    }

    public static void main(String[] args) {

        String path = "database";
        System.out.println("Generating SRG for Alloy models in " + path);


        String fp_SRG = "Results\\QualityIndicators\\SRG.txt";

        //deleting results files if they already exists
        try {
            Files.deleteIfExists(Paths.get(fp_SRG));
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

                        Graph<String, DefaultEdge> scg = new SimpleDirectedGraph<>(DefaultEdge.class);

                        System.out.println(filePath.toFile());
                        addSigs(scg, parser);

                        StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
                                new KosarajuStrongConnectivityInspector<>(scg);
                        List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
                                scAlg.getStronglyConnectedComponents();

                        // prints the strongly connected components
                        System.out.println(filePath.toFile());
                       System.out.println("Strongly connected components:");
                        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
                            System.out.println(stronglyConnectedSubgraphs.get(i));
                        }
                        System.out.println("SRG: " + stronglyConnectedSubgraphs.size());

                        try {
                            FileWriter SRGWriter = new FileWriter(fp_SRG, true);
                            SRGWriter.write(Integer.toString(stronglyConnectedSubgraphs.size()) + '\n');
                            SRGWriter.close();

                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }

                        //System.out.println(root);
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
