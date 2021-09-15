package com.alloyprofiling.retrievers;

import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SigRetriever {
    /**
     * Extracts and returns signatures from an Alloy model
     *
     * @param  type    the type of signatures to extract: all, one, lone, some, allmult (i.e. all
     *                 multiplicities), extensions, top (i.e. top-level sigs)
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of sigs
     */
    public static List<String> getSigs(String type, ALLOYParser parser, ParseTree tree) {

        ParseTreePattern p;

        switch (type.toLowerCase()) {
            case "all":
                //pattern to find all signature declarations
                p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;

            case "abstract":
                //pattern to find abstract signature declarations
                p = parser.compileParseTreePattern("<priv> abstract sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;

            case "abstractnofields":
                //pattern to find abstract signature declarations with no fields
                p = parser.compileParseTreePattern("<priv> abstract sig <names> <sigExtension> { } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;

            case "one":
                //pattern to find one sig names under sigDecl
                p = parser.compileParseTreePattern("<priv> one sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;

            case "onenofields":
                p = parser.compileParseTreePattern("<priv> one sig <names> <sigExtension> { } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;



            case "lone":
                p = parser.compileParseTreePattern("<priv> lone sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                break;

            case "some":
                p = parser.compileParseTreePattern("<priv> some sig <names> <sigExtension> { <decls> } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            case "allmult":
                p = parser.compileParseTreePattern("<priv> <mult> sig <names> <sigExtension> { <decls> } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            case "extensions":
                p = parser.compileParseTreePattern(
                        "<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            case "subsets":
                p = parser.compileParseTreePattern(
                        "<priv> <multiplicity> sig <names> in <superSet> { <decls> } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            case "top":
                p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> { <decls> } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            case "nofields":
                p = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { } <block_opt>",
                        ALLOYParser.RULE_sigDecl);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        List<ParseTreeMatch> sigMatches = p.findAll(tree, "//paragraph/*");

        List<String> sigNames = new ArrayList<>();

        //post-processing to pull out all sig names e.g. <mult> sig A, B { } counts as 2 sigs
        List<ParseTree> allNames = sigMatches.stream().map(match -> match.get("names")).collect(Collectors.toList());
        List<String> sigs = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
        for (String names : sigs) {
            String[] namesList = names.split(",");
            Collections.addAll(sigNames, namesList);

        }
        return  sigNames;
    }
}
