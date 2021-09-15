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

public class PredicateRetriever {
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
     * Extracts and returns predicate names from an Alloy model
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of predicates
     */
    public static List<String> getPredicates(ALLOYParser parser, ParseTree tree) {
        //pattern to find predicates names
        ParseTreePattern p_pred = parser.compileParseTreePattern("pred <nameID> <paraDecls_opt> <block>", ALLOYParser.RULE_predDecl);
        List<ParseTreeMatch> matches_pred = p_pred.findAll(tree, "//paragraph/*");

        //calling getNames to get all predicate names
        List<String> predicates = getNames(matches_pred);

        return predicates;

    }

    public static LinkedHashMap<String, Integer> countPredicateUses(ALLOYParser parser, ParseTree tree) {
        List<String> predicates = getPredicates(parser, tree);
        //XPath hierarchy path to find names under commands
        List<String> names_cmd = XPath.findAll(tree, "//cmdDecl/nameOrBlock/name",parser).stream()
                .map(ParseTree::getText).collect(Collectors.toList());

        //pattern to find names in all expressions
        Collection<ParseTree> predTrees_expr =  XPath.findAll(tree, "//expr//name", parser);
        //filter checks if the collected names are predicates
        List<String> predUses_expr = predTrees_expr.stream().map(p -> p.getText()).
                filter(predicates::contains).collect(Collectors.toList());

        LinkedHashMap<String, Integer> predicate_use_map = new LinkedHashMap<>();
        for (String pred: predicates) {
            int occurrences = Collections.frequency(names_cmd,  pred) + Collections.frequency(predUses_expr, pred);
            predicate_use_map.put(pred, occurrences);
        }
        return predicate_use_map;
    }
}
