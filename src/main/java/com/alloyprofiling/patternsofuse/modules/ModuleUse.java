package com.alloyprofiling.patternsofuse.modules;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
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

// class that counts the number of open statements that use each library module in Alloy
public class ModuleUse {
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
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Modules\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting standard library use for Alloy models in " + path);

        //files containing the number of open statements with each util module per model
        String fp_boolean = directoryName + "boolean.txt";
        String fp_graph = directoryName + "graph.txt";
        String fp_naturals = directoryName + "naturals.txt";
        String fp_ordering = directoryName + "ordering.txt";
        String fp_relation = directoryName + "relation.txt";
        String fp_ternary = directoryName + "ternary.txt";
        String fp_time = directoryName + "time.txt";
        String fp_seq = directoryName + "seq.txt";
        String fp_seqrel = directoryName + "seqrel.txt";
        String fp_sequence = directoryName + "sequence.txt";
        String fp_integer = directoryName +  "integer.txt";
        String fp_userModules = directoryName + "userModules.txt";


        List<String> fileNames = Arrays.asList(fp_boolean, fp_graph, fp_naturals, fp_ordering, fp_relation,
                fp_ternary, fp_time, fp_seq, fp_seqrel, fp_sequence, fp_integer);

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

                        //extracting user-declared modules (i.e. do not contain "util")
                        List<String> userModules = moduleNames.stream().filter(n-> !(n.contains("util"))).collect(Collectors.toList());


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

                        //enums implicitly import ordering --> increment ordering_count
                        List<String> topEnums = EnumRetriever.getEnums("top", parser, tree);
                        ordering_count += topEnums.size();

                        System.out.println(filePath.toFile());

                        List<String> allModules = Arrays.asList("boolean", "graph", "naturals", "ordering",
                                "relation", "ternary", "time", "sequniv", "seqrel", "sequence", "integer");
                        List<Integer> countList = Arrays.asList(boolean_count, graph_count, natural_count,
                                ordering_count, relation_count, ternary_count, time_count, seq_count,
                                seqrel_count, sequence_count, integer_count);


                        //mapping each module name to its counter
                        LinkedHashMap<String, Integer> countMap;
                        countMap = listsToMap(allModules, countList);
                        System.out.println(filePath.toFile());

                        //printing the counters for each module
                        for (Map.Entry<String, Integer> entry: countMap.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }

                        //printing counter for user modules
                        System.out.println("User-created modules: " + userModules.size());

                        //mapping result file names to the counters
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

                        //writing to file containing the number user-declared modules in each file
                        ResultWriter.writeResults(fp_userModules, userModules.size());

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
