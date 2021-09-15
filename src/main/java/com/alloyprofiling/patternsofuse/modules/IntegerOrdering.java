package com.alloyprofiling.patternsofuse.modules;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegerOrdering {
    private static int totIntOrd = 0;
    private static int totIntNoOrd = 0;
    private  static int totFields = 0;
    private static int totNotClassified = 0;
    private static List<String> getNames(List<ParseTreeMatch> match_list, String splitChar) {
        List<String> names = new ArrayList<>();
        if (splitChar.equals("rel"))
            for (ParseTreeMatch match: match_list) {
                String fieldNoSig = match.getTree().getText().split("\\.")[1];
                String[] split_str = fieldNoSig.split("[^\\w]+");
                names.add(split_str[0]);
            }
        else
            for (ParseTreeMatch match: match_list) {
                String fieldNoSig = match.getTree().getText().split("\\.")[1];
                String[] split_str = fieldNoSig.split(splitChar);
                names.add(split_str[0]);
            }

        return names;
    }

    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting integer uses that can/cannot be turned into ordering in Alloy models in " + path);

        //file containing number of int fields that can be turned into an ordering
        String fp_intOrdering = "Results\\ModelingPractices\\intOrdering.txt";
        //file containing number of int fields that cannot be turned into an ordering
        String fp_intNoOrdering = "Results\\ModelingPractices\\intNoOrdering.txt";

        //deleting results files if they already exit
        try {
            Files.deleteIfExists(Paths.get(fp_intOrdering));
            Files.deleteIfExists(Paths.get(fp_intNoOrdering));
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

                        //pattern to find field declarations with Int
                        ParseTreePattern p_intField = parser.compileParseTreePattern("<priv> <disjoint> <names> : <disj> Int <comma_opt>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches_intField = p_intField.findAll(tree, "//sigDecl//decls/*");

                        List<String> intFields = matches_intField.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //post-processing to pull out all field names i.e. f1,f2: Int counts as 2
                        List<String> intField_names = new ArrayList<>();
                        List<ParseTree> nameTrees = matches_intField.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> fields = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : fields) {
                            String[] namesList = names.split(",");
                            Collections.addAll(intField_names, namesList);
                        }

                        List<String> intField_names_noDup = intField_names.stream().distinct()
                                .collect(Collectors.toList());

                        if (!(intField_names.equals(intField_names_noDup)))
                            System.out.println("DUPLICATES: " + intField_names);

                        //pattern to field names used with relational operators
                        ParseTreePattern p_rel = parser.compileParseTreePattern( "<expr> <notOp> <rel_operators> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_rel = p_rel.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to field names used with an addition operator
                        ParseTreePattern p_add_op = parser.compileParseTreePattern("<expr> <add> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_add_op = p_add_op.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to field names used with a subtraction operator
                        ParseTreePattern p_sub_op = parser.compileParseTreePattern("<expr> <sub> <expr>", ALLOYParser.RULE_expr);
                        List<String> matches_sub_op = p_sub_op.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //pattern to field names used with Integer functions from the integer module
                        //addition (dot/join forms)
                        ParseTreePattern p_add_dot = parser.compileParseTreePattern("<expr>.add[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_add_dot = p_add_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_add_box = parser.compileParseTreePattern("add[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_add_box = p_add_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_plus_dot = parser.compileParseTreePattern("<expr>.plus[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_plus_dot = p_plus_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_plus_box = parser.compileParseTreePattern("plus[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_plus_box = p_plus_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //subtraction
                        ParseTreePattern p_sub_dot = parser.compileParseTreePattern("<expr>.sub[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_sub_dot = p_sub_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_sub_box = parser.compileParseTreePattern("sub[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_sub_box = p_sub_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_minus_dot = parser.compileParseTreePattern("<expr>.minus[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_minus_dot = p_minus_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_minus_box = parser.compileParseTreePattern("minus[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_minus_box = p_minus_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //multiplication
                        ParseTreePattern p_mult_dot = parser.compileParseTreePattern("<expr>.mult[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_mult_dot = p_mult_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_mult_box = parser.compileParseTreePattern("mult[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_mult_box = p_mult_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //division
                        ParseTreePattern p_div_dot = parser.compileParseTreePattern("<expr>.div[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_div_dot = p_div_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_div_box = parser.compileParseTreePattern("div[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_div_box = p_div_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        //remainder
                        ParseTreePattern p_rem_dot = parser.compileParseTreePattern("<expr>.rem[<expr>]", ALLOYParser.RULE_expr);
                        List<String> matches_rem_dot = p_rem_dot.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        ParseTreePattern p_rem_box = parser.compileParseTreePattern("rem[<exprs>]", ALLOYParser.RULE_expr);
                        List<String> matches_rem_box = p_rem_box.findAll(tree, "//*").stream().
                                map(m -> m.getTree().getText()).collect(Collectors.toList());

                        List<String> matches_num =  Lists.newArrayList(Iterables.concat(matches_add_op, matches_sub_op,
                                matches_add_box, matches_add_dot, matches_plus_box, matches_plus_dot, matches_sub_box,
                                matches_sub_dot, matches_minus_dot, matches_minus_box, matches_mult_box,
                                matches_mult_dot, matches_div_box, matches_div_dot, matches_rem_box, matches_rem_dot));

                        List<String> relNames = new ArrayList<>();
                        List<String> numNames = new ArrayList<>();

                        for (String field: intField_names) {
                            if (matches_num.stream().anyMatch(e->e.contains(field)))
                                numNames.add(field);

                            if (matches_rel.stream().anyMatch(e->e.contains(field)))
                                relNames.add(field);
                        }

                        System.out.println(filePath.toFile());
                        System.out.println("Int Fields: " + intField_names.size() + " " + intField_names);
                        totFields+= intField_names.size();

                        List<String> intOrdering = relNames.stream().filter(n -> !(numNames.contains(n))).distinct().
                                collect(Collectors.toList());

                        List<String> intNoOrdering =  numNames.stream().distinct().collect(Collectors.toList());


                        List<String> unclassified = intField_names.stream().filter(n-> !(relNames.contains(n)) &&
                                !(numNames.contains(n))).collect(Collectors.toList());

                        if (!unclassified.isEmpty()){
                            System.out.println("Not classified: " + unclassified );
                            totNotClassified += unclassified.size();
                        }

                        System.out.println("Can be made into ordering: " + intOrdering.size() + " " + intOrdering);
                        System.out.println("Cannot be made into ordering: " + intNoOrdering.size() + " " + intNoOrdering);

                        totIntOrd += intOrdering.size();
                        totIntNoOrd += intNoOrdering.size();

                        //writing result file containing the number of integer uses that can/cannot be made into an
                        //ordering
                        try {
                            FileWriter ordWriter = new FileWriter(fp_intOrdering, true);
                            ordWriter.write(Integer.toString(intOrdering.size()) + '\n');
                            ordWriter.close();

                            FileWriter noOrdWriter = new FileWriter(fp_intNoOrdering, true);
                            noOrdWriter.write(Integer.toString(intNoOrdering.size()) + '\n');
                            noOrdWriter.close();


                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total can be made into ordering: " + totIntOrd);
            System.out.println("Total cannot be made into ordering: " + totIntNoOrd);
            System.out.println("Total Int fields: " + totFields);
            System.out.println("Total unclassified: " + totNotClassified);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
