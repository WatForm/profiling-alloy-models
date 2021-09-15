package com.alloyprofiling.characteristics.cornercases;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
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

//class that count the number of relations declared using set union (f: A + B) and set difference (f: A- B)
public class RelUnionDiff {
    //counter for total number of relations declared using set union
    private static int totUnion = 0;
    //counter for total number of relations declared using set difference
    private static int totDiff = 0;
    //counter for total number of models containing relations declared using set union
    private static int unionModels = 0;
    //counter for total number of models containing relations declared using set difference
    private static  int diffModels = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\CornerCases\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting bit shifter operators in Alloy models in " + path);

        //result file containing the number of bit shifting operators in each model
        String fp_add = directoryName + "relUnion.txt";
        String fp_sub = directoryName + "relDiff.txt";

        //deleting result files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_add));
            Files.deleteIfExists(Paths.get(fp_sub));
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

                       //pattern to extract relations declared using set union (f: A + B)
                        ParseTreePattern p_union = parser.compileParseTreePattern(
                                "<priv> <disjoint> <names> : <disj> <expr> <add> <expr> <comma_opt>",
                                ALLOYParser.RULE_decl);

                        List<ParseTreeMatch> union_matches = p_union.findAll(tree, "//sigDecl/decls/*");

                        List<String> unionFields = new ArrayList<>();

                        //post-processing to pull out all rel names i.e. f1, f2: A + B counts as 2 fields
                        List<ParseTree> names_union = union_matches.stream().map(match -> match.get("names")).
                                collect(Collectors.toList());
                        List<String> fields_union = names_union.stream().map(ParseTree::getText).
                                collect(Collectors.toList());
                        for (String names : fields_union) {
                            String[] namesList = names.split(",");
                            Collections.addAll(unionFields, namesList);
                        }

                        //pattern to extract relations declared using set difference (f: A - B)
                        ParseTreePattern p_diff = parser.compileParseTreePattern(
                                "<priv> <disjoint> <names> : <disj> <expr> <sub> <expr> <comma_opt>",
                                ALLOYParser.RULE_decl);

                        List<ParseTreeMatch> diff_matches = p_diff.findAll(tree, "//sigDecl/decls/*");

                        List<String> diffFields = new ArrayList<>();

                        //post-processing to pull out all rel names i.e. f1, f2: A - B counts as 2 fields
                        List<ParseTree> names_diff = diff_matches.stream().map(match -> match.get("names")).
                                collect(Collectors.toList());
                        List<String> fields_diff = names_diff.stream().map(ParseTree::getText).
                                collect(Collectors.toList());
                        for (String names : fields_diff) {
                            String[] namesList = names.split(",");
                            Collections.addAll(diffFields, namesList);
                        }


                        //printing number of union/difference relations in each model
                        System.out.println(filePath.toFile());
                        System.out.println("Number of + fields: " + unionFields.size() + " " + unionFields);
                        System.out.println("Number of - fields: " + diffFields.size() + " " + diffFields);

                        //incrementing individual counters
                        totUnion += unionFields.size();
                        totDiff += diffFields.size();

                        //incrementing model counters
                        if (!(unionFields.isEmpty()))
                            unionModels++;
                        if (!(diffFields.isEmpty()))
                            diffModels++;

                        //writing result file containing the number of +/- fields in each file
                        ResultWriter.writeResults(fp_add, unionFields.size());
                        ResultWriter.writeResults(fp_sub, diffFields.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //printing total counters
            System.out.println("Total number of union (+) fields " + totUnion);
            System.out.println("Total number of difference (-) fields " + totDiff);
            System.out.println("Models with union (+): " + unionModels);
            System.out.println("Models with diff (-): " + diffModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
