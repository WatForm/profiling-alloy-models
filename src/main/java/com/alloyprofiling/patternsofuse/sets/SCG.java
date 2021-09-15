package com.alloyprofiling.patternsofuse.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that computes the SCG metric for Alloy models
public class SCG {

    /**
     * Creates a map that binds sub-components of signature declarations
     * The sub-components to be extracted are specified using the parameters key and value
     * @param matches list of ParseTreeMatches containing signature declarations
     * @param key name of sub-component of signature declaration to be extracted and used as key (e.g. names, superSet)
     * @param value name of sub-component of signature declaration to be extracted and used as value (e.g. name, names)
     * @return a LinkedHashMap that bind binds sub-components of signatures declarations
     */
    public static LinkedHashMap<ParseTree, ParseTree> listToMap(List<ParseTreeMatch> matches, String key, String value) {
        LinkedHashMap<ParseTree, ParseTree> map = new LinkedHashMap<>();
        for (ParseTreeMatch  m: matches) {
            map.put(m.get(key), m.get(value));
        }
        return map;
    }

    /**
     * Performs post-processing on a ParseTreeMatch of a signature declaration to extract and return the name of all the
     * signatures declared in the declaration
     * @param match ParseTreeMatch corresponding to a signature declaration
     * @return List of strings representing the names of signatures
     */
    private static List<String> getSigNames(ParseTreeMatch match) {
        //post-processing to pull out all sig names e.g. <mult> sig A, B { } counts as 2 sigs
        List<String> sigNames = new ArrayList<>();
        String[] namesList = match.get("names").getText().split(",");
        Collections.addAll(sigNames, namesList);
        return  sigNames;
    }

    /**
     * Adds all signatures in a model to the SCG and creates edges between signature nodes
     * @param scg Graph representing the SCG
     * @param parser instance of ALLOYParser used to parse the model
     */
    private static void addSigs(Graph<String, DefaultEdge> scg,  ALLOYParser parser) {
        //pattern to find extension sigs
        ParseTree tree = parser.specification();
        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

        // post-processing to add extensions and parent sigs to SRG
        Map<ParseTree,ParseTree> cpMap = listToMap(matches_children, "names", "name");

        //iterating over map of extensions and parents
        for (Map.Entry<ParseTree,ParseTree> entry: cpMap.entrySet()){
            String children = entry.getKey().getText();
            String parent = entry.getValue().getText();

            // adding parent to SCG if it is not already in it
            if (!(scg.containsVertex(parent)))
                scg.addVertex(parent);

            // adding extensions to SCG
            String[] namesList = children.split(",");
            for (String n : namesList) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
                // adding edges between parent and children
                createEdge(scg, parent, n);
            }
        }

        // getting map of enums (where the keys are top-level signatures and the values are lists of extensions)
        LinkedHashMap<String, List<String>> enumMap = EnumRetriever.getEnumMap(parser, tree);

        // iterating over entries in the map
        for (Map.Entry<String,List<String>> entry: enumMap.entrySet()) {

            // extracting top-level parent and enum extenions
            String parent = entry.getKey();
            List<String> enum_exts = entry.getValue();

            // adding parent to SCG if it is not already in it
            if (!(scg.containsVertex(parent)))
                scg.addVertex(parent);

            // adding enum extensions to SCG
            for (String n : enum_exts) {
                if (!(scg.containsVertex(n)))
                    scg.addVertex(n);
                // adding edges between parent and children
                createEdge(scg, parent, n);
            }
        }

        // pattern to find subset sigs
        ParseTreePattern p_subsets = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> in <superSet> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_subsets = p_subsets.findAll(tree, "//paragraph/*");

        // post-processing to add subsets and parent sigs to SRG
        Map<ParseTree,ParseTree> inMap = listToMap(matches_subsets, "superSet", "names");

        // post-processing to add subset sigs and superset sigs to SRG
        for (Map.Entry<ParseTree,ParseTree> entry: inMap.entrySet()){
            String superset = entry.getKey().getText();
            String subsets = entry.getValue().getText();

            // adding superset(s) to SRG if they are not already in the graph
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
            }

            // adding edges between parent and subset sigs
            for (String sub: subsetList) {
                for (String sup: supersetList) {
                    createEdge(scg, sub, sup);
                }
            }
        }

        // extracting remaining top-level signature sin the model
        List<String> topSigs = SigRetriever.getSigs("top", parser, tree);

        // post-processing to add remaining top-level sigs to SCG
        for (String name : topSigs) {
            if (!(scg.containsVertex(name)))
                scg.addVertex(name);
        }

        //pattern to find signature declarations
        ParseTreePattern p_sigDecls = parser.compileParseTreePattern(
                "<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block_opt>",
                ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches_sigDecls = p_sigDecls.findAll(tree, "//paragraph/*");

        // list containing built-in Alloy types
        List<String> builtInTypes = Arrays.asList("String", "Int", "seq/Int", "univ", "set");

        // post-processing: iterating over signature declarations
        for (ParseTreeMatch match: matches_sigDecls) {
            // extracting and storing names of signatures
            ArrayList<String> sigDecls = new ArrayList<>();
            String names = match.get("names").getText();
            String[] sigNames = names.split(",");
            Collections.addAll(sigDecls, sigNames);

            //extracting field/relation declarations
            ParseTree decls_tree = match.get("decls");

            // checking if signature declaration has any fields/ relations
            if (!(decls_tree.getText().isBlank())) {

                // extracting the names of signatures referenced in field/relation declarations
                List<String> name_decls =  XPath.findAll(decls_tree, "//expr//name", parser).stream().
                        map(t -> t.getText()).collect(Collectors.toList());
                // removing duplicates
                List<String> names_noDup = new ArrayList<>(
                        new HashSet<>(name_decls));

                // creating vertices corresponding to the signatures referenced in the field/relation declartion if
                // they are not already in the SCG
                for (String name: names_noDup) {
                    if (!(scg.containsVertex(name)) && !(builtInTypes.contains(name)))
                        scg.addVertex(name);
                }

                // creating vertices corresponding to the signatures declared if they are not already in the SCG
                for (String sig: sigDecls) {
                    if (!scg.containsVertex(sig))
                        scg.addVertex(sig);
                }

                // creating an edge between the signature declared and the signatures referenced in relations/fields
                for (String name: names_noDup) {
                    for (String sig: sigDecls ) {
                        if (!(builtInTypes.contains(name)) && !(name.equals(sig)) )
                            createEdge(scg, name, sig);
                    }
                }
            }
        }

        // getting all signature declaration packages
        ParseTreePattern p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");

        // getting a map that key is the name of a relation and the value is a list containing the signatures that
        // contain the relation
        LinkedHashMap<String, List<String>> relMap = RelationRetriever.getRelSigMap(parser, tree);
        List<String> relations = new ArrayList<>(relMap.keySet());

        // getting the names of all signatures in the model
        List<String> signatures = SigRetriever.getSigs("all", parser, tree);

        // map of coupled signatures
        LinkedHashMap<String, List<String>> coupledSigs = new LinkedHashMap<>();
        // map of coupled relations
        LinkedHashMap<List<String>, List<String>> coupledRelations = new LinkedHashMap<>();

        //iterating over signature declarations
        for (ParseTreeMatch match: matches) {
            //extracting the "block" and checking if it is empty
            ParseTree block = match.get("block");
            if (!StringUtils.strip( block.getText(), "{}").isBlank()) {
                // getting the names of signatures in the declaration
                List<String> sigs = getSigNames(match);

                // list that contains the names of all relations except those in the signature declaration "match"
                List<String> otherRelations = new ArrayList<>(relations);
                // removing each signature's relations to get relations that belong to other signatures
                List<String> mRelations = XPath.findAll(match.getTree(), "//decls//decl", parser).
                        stream().map(ParseTree::getText).collect(Collectors.toList());
                List<String> mRelNames = new ArrayList<>();
                mRelations.stream().forEach(r -> mRelNames.addAll(Arrays.asList(r.split(":")[0].
                        split(","))));
                otherRelations.removeAll(mRelNames);

                // list containing the names of all signatures in the model except those declared in "match"
                List<String> otherSets = new ArrayList<>(signatures);
                //removing each signature's name to get the names of other signatures
                otherSets.removeAll(getSigNames(match));

               // extracting the set (signatures + relations) names used in the body block of a signature declaration
                List<String> block_sets = XPath.findAll(block, "//expr//name", parser).stream().
                        filter(n -> signatures.contains(n.getText()) || relations.contains(n.getText())).
                        map(ParseTree::getText).collect(Collectors.toList());

                // if a relation belonging to another signature is referenced in the body of a signature declaration,
                // the "parent" signatures of the relation and the signatures in the current declaration are coupled
                otherRelations.forEach(r -> {
                    if (block_sets.contains(r))
                        coupledRelations.put(relMap.get(r), sigs);
                });

                // if a signature is referenced in the body of another signature, then this signature is coupled with
                // the signatures in the current declaration
                otherSets.forEach(s -> {
                    if (block_sets.contains(s))
                        coupledSigs.put(s, sigs);
                });

                //coupledRelations.forEach((key, value) -> System.out.println(key + ":" + value));

                // creating edges between signatures coupled with a relation reference
                coupledRelations.forEach((key, value) -> key.forEach( s1 -> value.forEach(s2 -> createEdge(scg, s1, s2))));

                //coupledSigs.forEach((key, value) -> System.out.println(key + ":" + value));

                // adding signatures to the SCG if they are not already in it
                coupledSigs.keySet().forEach(key -> {
                    if (!scg.containsVertex(key))
                        scg.addVertex(key);
                });

                // creating edges between signatures coupled with a signature name reference
                coupledSigs.forEach((key, value) -> value.forEach(s2 -> createEdge(scg, key, s2)));
            }
        }
    }

    /**
     * Creates a bi-directional edge between two distinct vertices v1, v2 (i.e. v1 != v2) in a Graph g
     * @param g Graph representing a SCG
     * @param v1 vertex in the graph
     * @param v2 vertex in the graph
     * @return an updated SCG with the newly created bi-directional edge between v1 and v2
     */
    private static Graph<String,DefaultEdge> createEdge(Graph<String, DefaultEdge> g, String v1, String v2) {
        g.addEdge(v1, v2);
        g.addEdge(v2, v1);
        return g;
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        
        String path = "corpus";
        System.out.println("Generating SCG for Alloy models in " + path);


        String fp_SCG = directoryName + "SCG.txt";

        //deleting results files if they already exists
        try {
            Files.deleteIfExists(Paths.get(fp_SCG));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterating over corpus of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));

                        // instantiating SCG
                        Graph<String, DefaultEdge> scg = new SimpleDirectedGraph<>(DefaultEdge.class);

                        System.out.println(filePath.toFile());

                        // calling addSigs to add signature vertices and edges to SCG
                        addSigs(scg, parser);


                        // creating a list that contains all strongly connected components of the SCG
                        StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
                                new KosarajuStrongConnectivityInspector<>(scg);
                        List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
                                scAlg.getStronglyConnectedComponents();

                        // prints the strongly connected components
                        System.out.println("Strongly connected components:");
                        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
                            System.out.println(stronglyConnectedSubgraphs.get(i));
                        }
                        System.out.println("SRG: " + stronglyConnectedSubgraphs.size());


                        // writing to results file
                        ResultWriter.writeResults(fp_SCG, stronglyConnectedSubgraphs.size());
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
