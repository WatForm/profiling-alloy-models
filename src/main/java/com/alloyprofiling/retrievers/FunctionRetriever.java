package com.alloyprofiling.retrievers;

import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionRetriever {
    private static List<String> getNames(List<ParseTreeMatch> matches)  {
        List<String> allNames = matches.stream().map(m -> m.get("nameID").getText()).
                collect(Collectors.toList());
        List<String> namesReceiver = allNames.stream().filter(n -> n.contains(".")).
                map(n -> n.split("\\.")[1]).
                collect(Collectors.toList());
        List<String> names = allNames.stream().filter(n -> !(n.contains("."))).
                collect(Collectors.toList());

        names.addAll(namesReceiver);
        return  names;
    }

    /**
     * Extracts and returns function names from an Alloy model
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of functions
     */
    public static List<String> getFunctions(ALLOYParser parser, ParseTree tree) {
        //pattern to find function names
        ParseTreePattern p_func = parser.compileParseTreePattern("<priv> fun <nameID> <paraDecls_opt> : <expr> {<expr>}", ALLOYParser.RULE_funDecl);
        List<ParseTreeMatch> matches_func = p_func.findAll(tree, "//paragraph/*");

        //calling getNames to get all function names
        List<String> functions = getNames(matches_func);

        return functions;
    }

    public static LinkedHashMap<String, Integer> countFunctionUses (ALLOYParser parser, ParseTree tree) {
        List<String> functions = getFunctions(parser, tree);
        //XPath hierarchy path to find names under commands
        List<String> names_cmd = XPath.findAll(tree, "//cmdDecl/nameOrBlock/name",parser).stream()
                .map(ParseTree::getText).collect(Collectors.toList());

        //pattern to find names in all expressions
        Collection<ParseTree> funcTrees_expr =  XPath.findAll(tree, "//expr//name", parser);
        //filter checks if the collected names are predicates
        List<String> funcUses_expr = funcTrees_expr.stream().map(p -> p.getText()).
                filter(functions::contains).collect(Collectors.toList());

        LinkedHashMap<String, Integer> function_use_map = new LinkedHashMap<>();
        for (String func: functions) {
            int occurrences = Collections.frequency(names_cmd,  func) + Collections.frequency(funcUses_expr, func);
            function_use_map.put(func, occurrences);
        }
        return function_use_map;
    }
}
