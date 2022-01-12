package com.alloyprofiling.patternsofuse.integers;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of integer fields that can be replaced by an ordering
public class IntegerOrdering {
    //counter for the total number of integer fields that can be replaced by an ordering
    private static int totIntOrd = 0;
    //counter for the total number of integer fields that CANNOT be replaced by an ordering
    private static int totIntNoOrd = 0;
    //counter for the total number of integer fields
    private  static int totFields = 0;
    //counter for the integer fields that are not classified (i.e. declared but not used).
    private static int totNotClassified = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Integers\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting integer uses that can/cannot be turned into ordering in Alloy models in " + path);

        //file containing number of int fields that can be turned into an ordering
        String fp_intOrdering = directoryName + "intOrdering.txt";
        //file containing number of int fields that cannot be turned into an ordering
        String fp_intNoOrdering = directoryName + "intNoOrdering.txt";

        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_intOrdering));
            Files.deleteIfExists(Paths.get(fp_intNoOrdering));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        //pattern to find field declarations with Int
                        ParseTreePattern p_intField = parser.compileParseTreePattern("<priv> <disjoint> <names> : <disj> Int <comma_opt>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches_intField = p_intField.findAll(tree, "//sigDecl//decls/*");

                        //post-processing to pull out all field names i.e. f1,f2: Int counts as 2
                        List<String> intFields = new ArrayList<>();
                        List<ParseTree> nameTrees = matches_intField.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> fields = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : fields) {
                            String[] namesList = names.split(",");
                            Collections.addAll(intFields, namesList);
                        }

                        //removing duplicates from the integer fields list
                        List<String> intFields_noDup = intFields.stream().distinct()
                                .collect(Collectors.toList());

                        //printing out all integer fields if list contains duplicates
                        if (!(intFields.equals(intFields_noDup)))
                            System.out.println("DUPLICATES: " + intFields);

                        //pattern to find field names used with relational operators
                        ParseTreePattern p_rel = parser.compileParseTreePattern( "<expr> <notOp> <rel_operators> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_rel = p_rel.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to find field names used with an addition operator
                        ParseTreePattern p_add_op = parser.compileParseTreePattern("<expr> <add> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_add_op = p_add_op.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to find field names used with a subtraction operator
                        ParseTreePattern p_sub_op = parser.compileParseTreePattern("<expr> <sub> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_sub_op = p_sub_op.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to find field names used with Integer functions from the integer module
                        //addition using add (dot form)
                        ParseTreePattern p_add_dot = parser.compileParseTreePattern("<expr>.add[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_add_dot = p_add_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //addition using add (box form)
                        ParseTreePattern p_add_box = parser.compileParseTreePattern("add[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_add_box = p_add_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //addition using plus (dot form)
                        ParseTreePattern p_plus_dot = parser.compileParseTreePattern("<expr>.plus[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_plus_dot = p_plus_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //addition using plus (box form)
                        ParseTreePattern p_plus_box = parser.compileParseTreePattern("plus[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_plus_box = p_plus_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //subtraction using sub (dot form)
                        ParseTreePattern p_sub_dot = parser.compileParseTreePattern("<expr>.sub[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_sub_dot = p_sub_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //subtraction using sub (box form)
                        ParseTreePattern p_sub_box = parser.compileParseTreePattern("sub[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_sub_box = p_sub_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //subtraction using minus (dot form)
                        ParseTreePattern p_minus_dot = parser.compileParseTreePattern("<expr>.minus[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_minus_dot = p_minus_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //subtraction using minus (box form)
                        ParseTreePattern p_minus_box = parser.compileParseTreePattern("minus[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_minus_box = p_minus_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //multiplication using mult (dot form)
                        ParseTreePattern p_mult_dot = parser.compileParseTreePattern("<expr>.mult[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_mult_dot = p_mult_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //multiplication using mult (box form)
                        ParseTreePattern p_mult_box = parser.compileParseTreePattern("mult[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_mult_box = p_mult_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //division using div (dot form)
                        ParseTreePattern p_div_dot = parser.compileParseTreePattern("<expr>.div[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_div_dot = p_div_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //division using div (box form)
                        ParseTreePattern p_div_box = parser.compileParseTreePattern("div[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_div_box = p_div_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //remainder using rem (dot form)
                        ParseTreePattern p_rem_dot = parser.compileParseTreePattern("<expr>.rem[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_rem_dot = p_rem_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //remainder using rem (box form)
                        ParseTreePattern p_rem_box = parser.compileParseTreePattern("rem[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_rem_box = p_rem_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //concatenating all matches
                        List<String> matches_num =  Lists.newArrayList(Iterables.concat(matches_add_op, matches_sub_op,
                                matches_add_box, matches_add_dot, matches_plus_box, matches_plus_dot, matches_sub_box,
                                matches_sub_dot, matches_minus_dot, matches_minus_box, matches_mult_box,
                                matches_mult_dot, matches_div_box, matches_div_dot, matches_rem_box, matches_rem_dot));

                        //list of integer field names used with relational operators exclusively
                        List<String> relNames = new ArrayList<>();
                        //list of integer field names used with numeric operators
                        List<String> numNames = new ArrayList<>();

                        //classifying integer fields
                        for (String field: intFields) {
                            if (matches_num.stream().anyMatch(e->e.contains(field)))
                                numNames.add(field);

                            if (matches_rel.stream().anyMatch(e->e.contains(field)))
                                relNames.add(field);
                        }

                        //printing out integer fields
                        System.out.println(filePath.toFile());
                        System.out.println("Int Fields: " + intFields.size() + " " + intFields);

                        //incrementing total integer field counter
                        totFields+= intFields.size();

                        //list of integer field names that can be turned into an ordering
                        List<String> intOrdering = relNames.stream().filter(n -> !(numNames.contains(n))).distinct().
                                collect(Collectors.toList());

                        //list of integer field names that cannot be turned into an ordering
                        List<String> intNoOrdering =  numNames.stream().distinct().collect(Collectors.toList());

                        //list of unclassified integer field names
                        List<String> unclassified = intFields.stream().filter(n-> !(relNames.contains(n)) &&
                                !(numNames.contains(n))).collect(Collectors.toList());

                        //printing out unclassified integer fields
                        if (!unclassified.isEmpty()){
                            System.out.println("Not classified: " + unclassified.size() + " " + unclassified );
                            totNotClassified += unclassified.size();
                        }

                        System.out.println("Can be made into ordering: " + intOrdering.size() + " " + intOrdering);
                        System.out.println("Cannot be made into ordering: " + intNoOrdering.size() + " " + intNoOrdering);

                        //incrementing counters
                        totIntOrd += intOrdering.size();
                        totIntNoOrd += intNoOrdering.size();

                        //writing result file containing the number of integer uses that can/cannot be made into an
                        //ordering
                        ResultWriter.writeResults(fp_intOrdering, intOrdering.size());
                        ResultWriter.writeResults(fp_intNoOrdering, intNoOrdering.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //printing counters over all files
            System.out.println("Total can be made into ordering: " + totIntOrd);
            System.out.println("Total cannot be made into ordering: " + totIntNoOrd);
            System.out.println("Total Int fields: " + totFields);
            System.out.println("Total unclassified: " + totNotClassified);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
