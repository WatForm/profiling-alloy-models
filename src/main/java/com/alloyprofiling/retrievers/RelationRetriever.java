package com.alloyprofiling.retrievers;

import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.util.*;

public class RelationRetriever {

    private static List<String> getSigNames(ParseTreeMatch match) {
        //post-processing to pull out all sig names e.g. <mult> sig A, B { } counts as 2 sigs
        List<String> sigNames = new ArrayList<>();
        String[] namesList = match.get("names").getText().split(",");
        Collections.addAll(sigNames, namesList);
        return  sigNames;
    }

    /**
     * Extracts and returns relation names from an Alloy model
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of relations
     */
    public static List<String> getRelations(ALLOYParser parser, ParseTree tree) {

        Collection<ParseTree> relationTrees = XPath.findAll(tree, "//sigDecl/decls/decl", parser);
        List<String> relations = new ArrayList<>();
        relationTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                .forEach(n -> relations.addAll(Arrays.asList(n.split(","))));

        return relations;

    }

    /**
     * Creates and returns a LinkedHashMap that relates relation names with their containing signatures
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a LinkedHashMap where the key is the name of a relation and the value
     *                 is a list containing the signatures.
     */
    public static LinkedHashMap<String, List<String>> getRelSigMap(ALLOYParser parser, ParseTree tree) {
        //pattern to find all signature declarations
        ParseTreePattern p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");
        LinkedHashMap<String, List<String>> sigRels = new LinkedHashMap<>();
        for (ParseTreeMatch m: matches) {
            List<String> sigs = getSigNames(m);
            Collection<ParseTree> relationTrees = XPath.findAll(m.getTree(), "//decls/decl", parser);
            if (!relationTrees.isEmpty()){
                List<String> relations = new ArrayList<>();
                relationTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                        .forEach(n -> relations.addAll(Arrays.asList(n.split(","))));
                relations.forEach(r -> sigRels.put(r, sigs));
            }
        }
        return sigRels;
    }

    /**
     * Creates and returns a LinkedHashMap that relates signatures with their fields
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a LinkedHashMap where the key is the name of a signature and the value
     *                 is a list containing the fields.
     */
    public static LinkedHashMap<String, List<String>> getSigRelMap (ALLOYParser parser, ParseTree tree) {
        //pattern to find all signature declarations
        ParseTreePattern p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
        List<ParseTreeMatch> matches = p.findAll(tree, "//paragraph/*");
        LinkedHashMap<String, List<String>> sigRels = new LinkedHashMap<>();
        for (ParseTreeMatch m: matches) {
            List<String> sigs = getSigNames(m);
            Collection<ParseTree> relationTrees = XPath.findAll(m.getTree(), "//decls/decl", parser);
            if (!relationTrees.isEmpty()){
                List<String> relations = new ArrayList<>();
                relationTrees.stream().map(ParseTree::getText).map(f -> f.split(":")[0])
                        .forEach(n -> relations.addAll(Arrays.asList(n.split(","))));
                sigs.forEach(s -> sigRels.put(s, relations));
            }
        }
        return sigRels;
    }
}
