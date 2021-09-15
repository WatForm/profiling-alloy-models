package com.alloyprofiling.unused.analysis;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Imports {
    private static int modelswithopen = 0;
    private static int modelswithutil = 0;
    private static int modelswithuser = 0;
    private static int totBoolean = 0;
    private static int totGraph = 0;
    private static int totNatural = 0;
    private static int totOrdering = 0;
    private static int totRelation = 0;
    private static int totTernary = 0;
    private static int totTime = 0;
    private static int totSequniv = 0;
    private static int totSeqrel= 0;
    private static int totSequence = 0;
    private static int totInteger = 0;
    private static int usable = 0;

    /**
     * Maps the strings in modules to the integer values in the countList
     * @param modules list of modules
     * @param countList list of integer
     * @return a LinkedHashMap that maps strings to integers
     */
    public static LinkedHashMap<String, Integer> listsToMap(List<String> modules, List<Integer>countList) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int ct = 0;
        for (String  m: modules) {
            map.put(m, countList.get(ct));
            ct++;
        }
        return map;
    }

    public static void main(String[] args) {
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get("benchmarks\\2021-06-03-12-31-50\\unsat_models.txt"))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String filename: result) {
            Path filepath = Paths.get("benchmarks\\2021-06-03-12-31-50\\" + filename);
            if (Files.isRegularFile(filepath)) {
                try {
                    String source = Files.readString(filepath);

                    ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                    ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                    ParseTree tree = parser.specification();

                    //pattern to find open commands
                    ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                    List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");

                    //post-processing to extract modules that contain "util"
                    List<ParseTree> allNames = matches.stream().map(match -> match.get("name")).collect(Collectors.toList());
                    List<String> moduleNames = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                    List<String> standardModules = moduleNames.stream().filter(n-> n.contains("util")).collect(Collectors.toList());

                    //extracting user-declared modules (i.e. do not contain "util")
                    List<String> userModules = moduleNames.stream().filter(n-> !(n.contains("util"))).collect(Collectors.toList());

                    System.out.println(filename);
                    System.out.println("Open statements: " + matches.size());
                    //printing counter for user modules
                    System.out.println("User-created modules: " + userModules.size());
                    System.out.println("Utility modules: " + standardModules.size());

                    if (!matches.isEmpty())
                        modelswithopen++;
                    else {
                        FileWriter filenamesWriter = new FileWriter("Results\\unsat_models_no_open.txt", true);
                        filenamesWriter.write(filename + '\n');
                        filenamesWriter.close();
                        usable++;
                    }

                    if (!userModules.isEmpty())
                        modelswithuser++;
                    if (!standardModules.isEmpty())
                        modelswithutil++;

                    //counter for each module
                    int boolean_count, graph_count, natural_count, ordering_count,
                            relation_count, ternary_count, time_count, seq_count,
                            seqrel_count, sequence_count, integer_count;

                    boolean_count = graph_count = natural_count = ordering_count =
                            relation_count = ternary_count = time_count =  seq_count =
                                    seqrel_count = sequence_count = integer_count = 0;

                    //counting the occurrences of each module
                    for(String module: standardModules) {
                        if (module.contains("boolean"))
                            boolean_count++;
                        else if (module.contains("graph"))
                            graph_count++;
                        else if (module.contains("natural"))
                            natural_count++;
                        else if (module.contains("ordering"))
                            ordering_count++;
                        else if (module.contains("relation"))
                            relation_count++;
                        else if (module.contains("ternary"))
                            ternary_count++;
                        else if (module.contains("time"))
                            time_count++;
                        else if (module.contains("sequniv"))
                            seq_count++;
                        else if (module.contains("seqrel"))
                            seqrel_count++;
                        else if (module.contains("sequence"))
                            sequence_count++;
                        else if (module.contains("integer"))
                            integer_count++;
                    }
                    totBoolean += boolean_count;
                    totNatural += natural_count;
                    totGraph += graph_count;
                    totOrdering += ordering_count;
                    totRelation += relation_count;
                    totSeqrel += seqrel_count;
                    totSequniv += seq_count;
                    totSequence += sequence_count;
                    totTernary += ternary_count;
                    totTime += time_count;
                    totInteger += integer_count;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total models with open: " + modelswithopen);
        System.out.println("Total models with utility: " + modelswithutil);
        System.out.println("Total models with user-modules: " + modelswithuser);
        System.out.println("Usable models: " + usable);

        System.out.println("Total boolean: " + totBoolean);
        System.out.println("Total graph: " + totGraph);
        System.out.println("Total natural: " + totNatural);
        System.out.println("Total ordering: " + totOrdering);
        System.out.println("Total relation: " + totRelation);
        System.out.println("Total ternary: " + totTernary);
        System.out.println("Total time: " + totTime);
        System.out.println("Total sequniv: " + totSequniv);
        System.out.println("Total seqrel: " + totSeqrel);
        System.out.println("Total sequence: " + totSequence);
        System.out.println("Total integer: " + totInteger);
    }
}
