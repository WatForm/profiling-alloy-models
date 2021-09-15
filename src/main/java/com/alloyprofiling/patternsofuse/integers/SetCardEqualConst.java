package com.alloyprofiling.patternsofuse.integers;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetCardEqualConst {
    //total counter for set cardinality <operator> constant integer across all models
    private static int totCardOpNum = 0;
    //total counter for # <sig> =/<=/=< <num> across all models
    private static int tot_sig_equal_le = 0;
    //total counter for # <formula> =/<=/=</>/</>= <num> across all models
    private static int totOtherUsesSigs = 0;
    private static int totOtherUsesExprs = 0;
    //total counter for set cardinality applications
    private static int totSetCard = 0;

    private static List<ParseTreeMatch> findCardPattern(List<String> patterns, ALLOYParser parser, ParseTree tree) {
        List<ParseTreeMatch> matches = new ArrayList<>();
        for (String pattern_str: patterns) {
            ParseTreePattern pattern = parser.compileParseTreePattern(
                    pattern_str, ALLOYParser.RULE_expr);
            matches.addAll(pattern.findAll(tree, "//expr"));
        }
        return matches;
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Integers\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting instances of set cardinality = integer constant in Alloy models in " + path);

        //file containing number of instances of # <sig> =/=</<= <num> per model
        String fp_card_sig_equal_le = directoryName + "cardSigEqualLE.txt";

        String fp_otherUsesSigs = directoryName + "cardOtherUsesSigs.txt";
        String fp_otherUsesExprs = directoryName + "cardOtherUsesExprs.txt";

        //file containing the integer constants used with # <sig> =/=</<= <num>
        String fp_intConstSig = directoryName + "cardIntSigs.txt";


        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_card_sig_equal_le));
            Files.deleteIfExists(Paths.get(fp_otherUsesSigs));
            Files.deleteIfExists(Paths.get(fp_otherUsesExprs));
            Files.deleteIfExists(Paths.get(fp_intConstSig));
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

                        //XPath hierarchy path to find all set cardinality applications
                        Collection<ParseTree> scTrees =  XPath.findAll(tree, "//unOp/setCard", parser);

                        //getting all signature names from model using SigRetriever
                        List<String> signatures = SigRetriever.getSigs("all", parser, tree);


                        //finding instances of set cardinality = integer constant and integer constant = set cardinality
                        List<ParseTreeMatch> matches_equal_le = findCardPattern(
                                Arrays.asList("# <expr> = <num>", "<num> = # <expr>","# <expr> \\<= <num>", "<num> \\<= # <expr>",
                                        "# <expr> =\\< <num>", "<num> =\\< # <expr>"), parser, tree);

                        List<String> expr_equal_le = matches_equal_le.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //extracting the signatures that set cardinality is applied to (for # <sig> = <num>)
                        List<String> sigs = matches_equal_le.stream().map(m -> m.get("expr")).map(ParseTree::getText).
                                filter(signatures::contains).collect(Collectors.toList());

                        //extracting the integer constants used with # sig =/<=/=< integer ONLY
                        List<ParseTreeMatch> card_with_sigs = matches_equal_le.stream().filter(m ->
                                signatures.contains(m.get("expr").getText())).collect(Collectors.toList());

                        List<String> int_const_sigs = card_with_sigs.stream().map(m -> m.get("num")).map(ParseTree::getText).
                                collect(Collectors.toList());


                        //extracting the formulas that set cardinality is applied to
                        List<String> formulas_equal_le = matches_equal_le.stream().map(m -> m.get("expr")).map(ParseTree::getText).
                                filter(e -> !(signatures.contains(e))).collect(Collectors.toList());

                        //writing integer values used with set cardinality operator to txt file
                        int_const_sigs.forEach(i -> ResultWriter.writeResults(fp_intConstSig, Integer.parseInt(i)));

                        //finding instances of set cardinality </>/>= integer constant and integer constant </>/>= set cardinality
                        List<ParseTreeMatch> matches_other_operators = findCardPattern(
                                Arrays.asList("# <expr> \\< <num>", "<num> \\< # <expr>","# <expr> \\>= <num>", "<num> \\>= # <expr>",
                                        "# <expr> \\> <num>", "<num> \\> # <expr>"), parser, tree);

                        List<String> expr_other_operators = matches_other_operators.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());


                        //extracting the formulas that set cardinality is applied to with </>/>=
                        List<String> other_formulas = matches_other_operators.stream().map(m -> m.get("expr")).
                                map(ParseTree::getText).filter(e -> !(signatures.contains(e)))
                                .collect(Collectors.toList());

                        List<String> all_formulas = Stream.concat(formulas_equal_le.stream(), other_formulas.stream()).
                                collect(Collectors.toList());

                        //extracting the sigs that set cardinality is applied to with </>/>=
                        List<String> other_sigs = matches_other_operators.stream().map(m -> m.get("expr")).
                                map(ParseTree::getText).filter(e -> signatures.contains(e))
                                .collect(Collectors.toList());


                        List<String> expr_strings = Stream.concat(expr_equal_le.stream(), expr_other_operators.stream())
                                .collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("Set cardinality <operator> integer constant: " + expr_strings.size() + " " +
                                expr_strings);

                        System.out.println("# <sig> =/=</<= <num>: " + sigs.size() + " " + sigs);
                        System.out.println("# <sig> </>/>= <num>: " + other_sigs.size() + " " + other_sigs);
                        System.out.println("# <formula> </>/>=/=/<=/=> <num>: " + all_formulas.size() + " " + all_formulas);

                        //incrementing total counters
                        totCardOpNum += expr_strings.size();
                        tot_sig_equal_le += sigs.size();
                        totOtherUsesSigs += other_sigs.size();
                        totOtherUsesExprs += all_formulas.size();
                        totSetCard += scTrees.size();

                        //writing results files
                        ResultWriter.writeResults(fp_card_sig_equal_le, sigs.size());
                        ResultWriter.writeResults(fp_otherUsesSigs, other_sigs.size());
                        ResultWriter.writeResults(fp_otherUsesExprs, all_formulas.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total set cardinality applications: " + totSetCard);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            System.out.println("Total set cardinality <operator> integer constant: " + totCardOpNum + " i.e. " +
                    df.format(totCardOpNum * 100.0 / totSetCard) +"% of all set cardinality applications");
            System.out.println("Total # <sig> =/<=/=> <num>: " + tot_sig_equal_le + " i.e. " +
                    df.format(tot_sig_equal_le * 100.0 / totSetCard) +"% of all set cardinality applications");
            System.out.println("Total # <sig> </>/>= <num>: " + totOtherUsesSigs +
                    " i.e. " + df.format(totOtherUsesSigs * 100.0 / totSetCard) +"% of all set cardinality applications");
            System.out.println("Total # <formula> </>/>=/=/<=/=> <num>: " + totOtherUsesExprs +
                    " i.e. " + df.format(totOtherUsesExprs * 100.0 / totSetCard) +"% of all set cardinality applications");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
