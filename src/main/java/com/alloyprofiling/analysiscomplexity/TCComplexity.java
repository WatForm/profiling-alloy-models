package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.FunctionRetriever;
import com.alloyprofiling.retrievers.PredicateRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TCComplexity {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting number of sets referenced in TC operators in Alloy models in " + path);

        //file containing the complexity of the formula that have TC applied to them across all models
        String fp_tcc = "Results\\AnalysisComplexity\\tcc.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_tcc));
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
                        ParseTree tree = parser.specification();

                        //calling getPredicates to get all predicate names + adding dots
                        List<String> predNames = PredicateRetriever.getPredicates(parser, tree).
                                stream().map(n -> "." + n)
                                .collect(Collectors.toList());
                        //System.out.println("predicates: " + predNames);

                        //pattern to find function names
                        ParseTreePattern p_func = parser.compileParseTreePattern("<priv> fun <nameID> <paraDecls_opt> : <expr> {<expr>}", ALLOYParser.RULE_funDecl);
                        List<ParseTreeMatch> matches_func = p_func.findAll(tree, "//paragraph/*");

                        //calling getFunctions to get all function names + adding dots
                        List<String> funcNames = FunctionRetriever.getFunctions(parser, tree).
                                stream().map(n -> "." + n)
                                .collect(Collectors.toList());;
                        //System.out.println("functions: " + funcNames);

                        //pattern to find all applications of the transitive closure operators
                        ParseTreePattern p_tcExprs = parser.compileParseTreePattern("<tcOp> <expr>", ALLOYParser.RULE_expr);
                        List<ParseTreeMatch> matches = p_tcExprs.findAll(tree, "//*");

                        System.out.println(filePath.toFile());

                        for (ParseTreeMatch m: matches) {
                            System.out.println(m.get("expr").getText());
                            String expr = m.get("expr").getText();
                            int complexity;
                            Collection<ParseTree> names = XPath.findAll(m.getTree(), "//name", parser);
                            System.out.println("Count of sets: " + names.size());
                            complexity = names.size();
                            // counting the arguments of functions/predicates
                            if (expr.contains("[") && expr.contains("]")) {
                                String params = StringUtils.substringBetween(expr, "[", "]");
                                String[] params_split = params.split(",");
                                if (funcNames.stream().anyMatch(expr::contains) ||
                                        predNames.stream().anyMatch(expr::contains))
                                    complexity += params_split.length + 1;
                                else
                                    complexity += params_split.length;

                            }

                            System.out.println("Complexity: " + complexity);
                            //writing result file containing the complexity of transitive closure per application
                            try {
                                FileWriter tccWriter = new FileWriter(fp_tcc, true);
                                tccWriter.write(Integer.toString(complexity) + '\n');
                                tccWriter.close();

                            } catch (IOException e) {
                                System.out.println("An error writing files occurred.");
                                e.printStackTrace();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
