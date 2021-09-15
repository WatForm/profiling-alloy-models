package com.alloyprofiling.retrievers;

import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.util.*;
import java.util.stream.Collectors;

public class EnumRetriever {
    /**
     * Extracts and returns enums from an Alloy model
     *
     * @param  type    the type of enums to extract: all, top, or extensions
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of enums
     */
    public static List<String> getEnums(String type, ALLOYParser parser, ParseTree tree) {
        //pattern to find enum declarations
        ParseTreePattern p_enum = parser.compileParseTreePattern("enum <name> { <names> }", ALLOYParser.RULE_enumDecl);
        List<ParseTreeMatch> matches_enum = p_enum.findAll(tree, "//paragraph/*");
        List<String> enumList = new ArrayList<>();
        List<String> topSigs;
        List<String> enums;
        switch (type) {
            case "all":
                //post-processing to add all top sigs
                topSigs  = matches_enum.stream().map(match -> match.get("name")).
                        map(ParseTree::getText).collect(Collectors.toList());
                enumList.addAll(topSigs);
                //post-processing to pull out all enums names i.e. enum Time {Morning, Noon, Night} counts as 3
                enums = matches_enum.stream().map(match -> match.get("names")).
                        map(ParseTree::getText).collect(Collectors.toList());
                for (String names : enums) {
                    String[] enumNames = names.split(",");
                    Collections.addAll(enumList, enumNames);
                }
                break;

            case "top":
                //post-processing to add all top enums
                topSigs  = matches_enum.stream().map(match -> match.get("name")).
                        map(ParseTree::getText).collect(Collectors.toList());
                enumList.addAll(topSigs);
                break;

            case "extensions":
                //post-processing to pull out all enums names i.e. enum Time {Morning, Noon, Night} counts as 3
                enums = matches_enum.stream().map(match -> match.get("names")).
                        map(ParseTree::getText).collect(Collectors.toList());
                for (String names : enums) {
                    String[] enumNames = names.split(",");
                    Collections.addAll(enumList, enumNames);
                }
                break;

        }
        return  enumList;
    }

    /**
     * Creates and returns a LinkedHashMap that relates top-level enums with their extensions
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a LinkedHashMap where the key is the name the top-level enum and the value
     *                 is a list containing the extensions.
     */
    public static LinkedHashMap<String, List<String>> getEnumMap(ALLOYParser parser, ParseTree tree) {
        //pattern to find enum declarations
        ParseTreePattern p_enum = parser.compileParseTreePattern("enum <name> { <names> }", ALLOYParser.RULE_enumDecl);
        List<ParseTreeMatch> matches_enum = p_enum.findAll(tree, "//paragraph/*");
        LinkedHashMap<String, List<String>> enumMap = new LinkedHashMap<>();
        for (ParseTreeMatch m: matches_enum) {
            String topSig = m.get("name").getText();
            List<String> extensions = new ArrayList<>();
            List<String> enum_ext = m.getAll("names").stream().map(ParseTree::getText).
                    collect(Collectors.toList());
            for (String names : enum_ext) {
                String[] namesList = names.split(",");
                Collections.addAll(extensions, namesList);

            }

            enumMap.put(topSig, extensions);
        }
        return  enumMap;
    }
}
