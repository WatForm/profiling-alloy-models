package com.alloyprofiling.analysiscomplexity.depthjoinsquant;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepthJoin {
    private static int totBox = 0;
    private static int totDot = 0;
    public static List<Integer> getDepthJoin(String path) {
        Path filePath = Paths.get(path);
        List<Integer> depths = new ArrayList<>();
        if (Files.isRegularFile(filePath)) {
            try {
                String source = Files.readString(filePath);

                ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                ParseTree tree = parser.specification();

                //computing depth of joins
                //pattern to find joins without quant
                ParseTreePattern p_joins = parser.compileParseTreePattern("<expr> <dotOp> <expr>", ALLOYParser.RULE_expr);
                List<ParseTreeMatch> matches_joins = p_joins.findAll(tree, "//*");

                List<String> fullJoins = new ArrayList<>();
                for (ParseTreeMatch m : matches_joins) {
                    if (!(fullJoins.contains(m.getTree().getParent().getText())))
                        fullJoins.add(m.getTree().getText());
                }

                List<String> distFullJoins = fullJoins.stream().distinct().collect(Collectors.toList());

                List<String[]> decomposed_joins = distFullJoins.stream().map(j -> j.split("\\.")).
                        collect(Collectors.toList());

                //writing depth of join for each expression
                for (String[] s : decomposed_joins) {
                    depths.add(s.length);
                }

                return depths;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return depths;
    }
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\AnalysisComplexity\\DepthJoinQuantification\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting dot and box joins in Alloy models in " + path);

        //file containing the depth of join of each formula in all models
        String fp_DOJ = directoryName + "DOJ.txt";
        //file containing the number of uses of dot join per model
        String fp_dotJoin = directoryName + "dotJoin.txt";
        //file containing the number of uses of box join per model
        String fp_boxJoin = directoryName + "boxJoin.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_DOJ));
            Files.deleteIfExists(Paths.get(fp_dotJoin));
            Files.deleteIfExists(Paths.get(fp_boxJoin));
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

                        //XPath string  path to find all dot joins under expressions
                        Collection<ParseTree> dotTrees =  XPath.findAll(tree, "//expr/binOp/dotOp", parser);

                        System.out.println(filePath.toFile());
                        System.out.println("Dot Joins: " + dotTrees.size());
                        totDot += dotTrees.size();

                        //pattern to find box joins
                        ParseTreePattern p_box = parser.compileParseTreePattern("<expr> [<expr>]", ALLOYParser.RULE_expr);
                        List<ParseTreeMatch> matches_box = p_box.findAll(tree, "//*");

                        //pattern to find all function names
                        ParseTreePattern p_functions = parser.compileParseTreePattern("<priv> fun <nameID> <paraDecls_opt> : <expr> {<expr>}", ALLOYParser.RULE_funDecl);
                        List<ParseTreeMatch> matches_func = p_functions.findAll(tree, "//paragraph/*");

                        //pattern to find predicates
                        ParseTreePattern p_predicates = parser.compileParseTreePattern("pred <nameID> <paraDecls_opt> <block>", ALLOYParser.RULE_predDecl);
                        List<ParseTreeMatch> matches_pred = p_predicates.findAll(tree, "//paragraph/*");

                        //all functions
                        List<String> funcs = matches_func.stream().map(m -> m.get("nameID")).collect(Collectors.toList()).
                                stream().map(ParseTree::getText).collect(Collectors.toList());

                        //all predicates
                        List<String> preds = matches_pred.stream().map(m -> m.get("nameID")).collect(Collectors.toList()).
                                stream().map(ParseTree::getText).collect(Collectors.toList());


                        //cross-referencing box matches with predicate and function names to exclude function
                        //and predicate calls
                        List<ParseTreeMatch> boxJoins = new ArrayList<>();
                        for(ParseTreeMatch m: matches_box) {
                            List<ParseTree> exprs = m.getAll("expr");
                            List<String> names = exprs.stream().map(e -> e.getText()).collect(Collectors.toList());
                            if (!(funcs.contains(names.get(0))) && !(preds.contains(names.get(0))))
                                boxJoins.add(m);
                        }
                        System.out.println("Box Joins: " + boxJoins.size());
                        totBox += boxJoins.size();

                        //computing depth of joins
                        //pattern to find joins without quant
                        ParseTreePattern p_joins = parser.compileParseTreePattern("<expr> <dotOp> <expr>", ALLOYParser.RULE_expr);
                        List<ParseTreeMatch> matches_joins = p_joins.findAll(tree, "//*");

                        //pattern to find joins with quant
                        /*
                        ParseTreePattern p_joins = parser.compileParseTreePattern("<quant> <expr> <binOp> <expr>", ALLOYParser.RULE_expr);
                        List<ParseTreeMatch> matches_joins = p_box.findAll(tree, "//*"); */

                        List<String> fullJoins = new ArrayList<>();
                        for (ParseTreeMatch m: matches_joins) {
                            if(!(fullJoins.contains(m.getTree().getParent().getText())))
                                fullJoins.add(m.getTree().getText());
                        }

                        List<String> distFullJoins = fullJoins.stream().distinct().collect(Collectors.toList());

                        List<String[]> decomposed_joins = distFullJoins.stream().map(j -> j.split("\\.")).
                                collect(Collectors.toList());

                        //writing depth of join for each expression
                        for (String[] s: decomposed_joins){
                            try {
                                System.out.println("Depth: " + s.length);
                                FileWriter dojWriter = new FileWriter(fp_DOJ, true);
                                dojWriter.write(Integer.toString(s.length) + '\n');
                                dojWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //writing result file containing the number of dot/box joins in each file
                        try {
                            FileWriter dotWriter = new FileWriter(fp_dotJoin, true);
                            dotWriter.write(Integer.toString(dotTrees.size()) + '\n');
                            dotWriter.close();

                            FileWriter boxWriter = new FileWriter(fp_boxJoin, true);
                            boxWriter.write( Integer.toString(boxJoins.size())  + '\n');
                            boxWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of dot joins: " + totDot);
            System.out.println("Total number of box joins: " + totBox);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
