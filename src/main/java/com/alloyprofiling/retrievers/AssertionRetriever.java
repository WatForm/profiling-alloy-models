package com.alloyprofiling.retrievers;

import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionRetriever {
    /**
     * Extracts and returns assertion names from an Alloy model
     *
     * @param parser   ALLOYParser instantiated with a CommonTokenStream from an ALLOYLexer
     * @param tree     the parse tree generated from a start rule call on the Alloy parser
     * @return         a list of string containing the names of assertions
     */
    public static List<String> getAssertions(ALLOYParser parser, ParseTree tree) {
        //pattern to find assertion names
        Collection<ParseTree> assertTrees = XPath.findAll(tree, "//assertDecl/name_opt/name", parser);

        List<String> assertions = assertTrees.stream().map(t -> t.getText()).collect(Collectors.toList());

        return  assertions;
    }
}
