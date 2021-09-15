package com.alloyprofiling.patternsofuse.modules;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StandardLib {
    public static LinkedHashMap<String, Integer> listsToMap(List<String> moduleCount, List<Integer>countList) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int ct = 0;
        for (String  m: moduleCount) {
            map.put(m, countList.get(ct));
            ct++;
        }
        return map;
    }

    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing standard library use for Alloy models in " + path);

        //files containing the number of open statements with each util module per model
        String fp_boolean = "Results\\ModelingPractices\\boolean.txt";
        String fp_graph = "Results\\ModelingPractices\\graph.txt";
        String fp_naturals = "Results\\ModelingPractices\\naturals.txt";
        String fp_ordering = "Results\\ModelingPractices\\ordering.txt";
        String fp_relation = "Results\\ModelingPractices\\relation.txt";
        String fp_ternary = "Results\\ModelingPractices\\ternary.txt";
        String fp_time = "Results\\ModelingPractices\\time.txt";
        String fp_seq = "Results\\ModelingPractices\\seq.txt";
        String fp_seqrel = "Results\\ModelingPractices\\seqrel.txt";
        String fp_sequence = "Results\\ModelingPractices\\sequence.txt";


        List<String> fileNames = Arrays.asList(fp_boolean, fp_graph, fp_naturals, fp_ordering, fp_relation,
                fp_ternary, fp_time, fp_seq, fp_seqrel, fp_sequence);
        //deleting results files if they already exit
        try {
            for(String filename: fileNames){
                Files.deleteIfExists(Paths.get(filename));
            }
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

                        //pattern to find open commands
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");


                        //post-processing to extract modules that contain "util"
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> moduleNames = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        List<String> standardModules = moduleNames.stream().filter(n-> n.contains("util")).collect(Collectors.toList());

                        int boolean_count, graph_count, natural_count, ordering_count,
                                relation_count, ternary_count, time_count, seq_count,
                                seqrel_count, sequence_count;

                        boolean_count = graph_count = natural_count = ordering_count =
                                relation_count = ternary_count = time_count =  seq_count =
                                        seqrel_count = sequence_count = 0;

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
                        }

                        List<String> topEnums = EnumRetriever.getEnums("top", parser, tree);
                        ordering_count += topEnums.size();

                        System.out.println(filePath.toFile());
                        List<String> allModules = Arrays.asList("boolean", "graph", "naturals", "ordering",
                                "relation", "ternary", "time", "sequniv", "seqrel", "sequence");
                        List<Integer> countList = Arrays.asList(boolean_count, graph_count, natural_count,
                                ordering_count, relation_count, ternary_count, time_count, seq_count,
                                seqrel_count, sequence_count);


                        LinkedHashMap<String, Integer> countMap;
                        countMap = listsToMap(allModules, countList);
                        System.out.println(filePath.toFile());
                        for (Map.Entry<String, Integer> entry: countMap.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }
                        LinkedHashMap<String, Integer> fileCountMap;
                        fileCountMap = listsToMap(fileNames, countList);

                        //writing result files containing the number of module uses
                        try {

                            for (Map.Entry<String, Integer > entry: fileCountMap.entrySet()) {
                                FileWriter myWriter = new FileWriter(entry.getKey(), true);
                                myWriter.write(Integer.toString(entry.getValue()) + '\n');
                                myWriter.close();
                            }

                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
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
