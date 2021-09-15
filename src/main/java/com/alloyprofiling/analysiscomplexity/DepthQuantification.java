package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepthQuantification {
    private static void computeDOQ(List<ParseTreeMatch> matches, ALLOYParser parser, String fp_DOQ) {
        //post-processing to pull out quantified sub-expressions
        for (ParseTreeMatch m: matches) {
            String formula = m.getTree().getText();
            System.out.println("Formula: " + formula);
            String[] formula_split = formula.split("\\|");

            ParseTree sub = m.get("blockOrBar");
            ParseTreePattern p_subQuant = parser.compileParseTreePattern("<quant> <decls_e> <blockOrBar>", ALLOYParser.RULE_expr);
            List<ParseTreeMatch> matches_subQuant = p_subQuant.findAll(sub, "//*");
            List<String> subQuants = matches_subQuant.stream().map(t -> t.getTree().getParent().getText()).
                    collect(Collectors.toList());

            System.out.println(subQuants);
            if (!subQuants.isEmpty()) {
                String[] new_split;
                if (formula_split.length > 1)
                    new_split = Arrays.copyOfRange(formula_split, 1, formula_split.length - 1);
                else
                    new_split = formula_split;

                boolean valid = true;
                List<String> quantifiers = Arrays.asList("all", "some", "no", "one", "lone");
                for (String sub_expr: new_split) {
                    if(!quantifiers.stream().anyMatch(q -> sub_expr.startsWith(q)))
                        valid = false;
                }
                if (valid) {
                    //writing result files containing DOQ per expression
                    System.out.println("DOQ: " + (subQuants.size() + 1));
                    try {
                        FileWriter doqWriter = new FileWriter(fp_DOQ, true);
                        doqWriter.write(Integer.toString(subQuants.size()+1) + '\n');
                        doqWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error writing files occurred.");
                        e.printStackTrace();
                    }
                }
            }
            else {
                //writing result files containing DOQ per expression
                System.out.println("DOQ: " + (subQuants.size() + 1));
                try {
                    FileWriter doqWriter = new FileWriter(fp_DOQ, true);
                    doqWriter.write(Integer.toString(subQuants.size()+1) + '\n');
                    doqWriter.close();
                } catch (IOException e) {
                    System.out.println("An error writing files occurred.");
                    e.printStackTrace();
                }
            }


        }
    }
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing depth of quantification in Alloy models in " + path);

        //file containing the depth of quantification per formula per model
        String fp_DOQ = "Results\\AnalysisComplexity\\DOQ.txt";

        //deleting results files if they already exists
        try {
            Files.deleteIfExists(Paths.get(fp_DOQ));
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

                        //pattern to find quantified expressions
                        ParseTreePattern p_quantExpr = parser.compileParseTreePattern("<quant> <decls_e> <blockOrBar>", ALLOYParser.RULE_expr);
                        List<ParseTreeMatch> matches = p_quantExpr.findAll(tree, "//*");
                        List<ParseTreeMatch> matches_top = matches.stream().
                                filter(t -> t.getTree().getParent() instanceof ALLOYParser.BlockContext).collect(Collectors.toList());

                        List<String> matches_topString = matches_top.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());
                        List<String> matches_string = matches.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        matches_string.removeAll(matches_topString);
                        List<ParseTreeMatch> matches_filtered =  matches.stream().filter(m -> !(matches_topString.stream().
                                anyMatch(t -> t.contains(m.getTree().getText())))).collect(Collectors.toList());


                        System.out.println(filePath.toFile());
                        computeDOQ(matches_top, parser, fp_DOQ);
                        computeDOQ(matches_filtered, parser, fp_DOQ);

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
