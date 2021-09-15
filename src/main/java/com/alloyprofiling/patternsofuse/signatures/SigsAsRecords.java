package com.alloyprofiling.patternsofuse.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SigsAsRecords {
    private static int totRecords = 0;
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting signatures used as records in Alloy models in " + path);

        //file containing the number of signatures used as records per model
        String fp_records = "Results\\ModelingPractices\\records.txt";

        //deleting results file if it already exists

        try {
            Files.deleteIfExists(Paths.get(fp_records));
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


                        //map between signature names and their correspondent fields
                        LinkedHashMap<String, List<String>> relMap = RelationRetriever.getSigRelMap(parser, tree);

                        relMap.entrySet().forEach(entry->{
                            System.out.println(entry.getKey() + " " + entry.getValue());
                        });

                        //getting all signature names
                        List<String> totalSigs = SigRetriever.getSigs("all", parser, tree);

                        List<String> records = new ArrayList<>();

                        for (String sig: totalSigs) {
                            //System.out.println(sig);
                            //list containing sig's fields
                            List<String> fields = relMap.get(sig);
                            if (fields != null && !fields.isEmpty())
                            {
                                //pattern to find dot joins that bind sig and one of its fields
                                ParseTreePattern p_dot = parser.compileParseTreePattern(sig + " <dotOp> <expr>", ALLOYParser.RULE_expr);
                                List<String> matches_dot = p_dot.findAll(tree, "//*").stream().
                                        map(m -> m.getTree().getText()).filter(s -> fields.stream().anyMatch(s::contains)
                                        && !(s.contains("*") || s.contains("^"))).collect(Collectors.toList());
                                //pattern to find box joins that bind sig and one of its fields
                                ParseTreePattern p_boxJ = parser.compileParseTreePattern("<expr> [" + sig + "]", ALLOYParser.RULE_expr);
                                List<String> matches_boxJ = p_boxJ.findAll(tree, "//*").stream().
                                        map(m-> m.getTree().getText()).filter(s -> fields.stream().anyMatch(s::contains)).
                                        collect(Collectors.toList());

                                //finding all other references to sig in the model
                                ParseTreePattern p_sigs_expr = parser.compileParseTreePattern(sig, ALLOYParser.RULE_name);
                                List<String> matches_sigs_expr = p_sigs_expr.findAll(tree, "//expr/*").stream().
                                        map(m -> m.getTree().getText()).collect(Collectors.toList());

                                if (!matches_sigs_expr.isEmpty() && matches_sigs_expr.size() == (matches_dot.size() + matches_boxJ.size()))
                                    records.add(sig);
                            }

                        }

                        System.out.println(filePath.toFile());

                        totRecords += records.size();
                        System.out.println("Signatures used as records: " + records.size());
                        System.out.println(records);

                        try {
                            FileWriter mscWrite = new FileWriter(fp_records, true);
                            mscWrite.write(Integer.toString(records.size()) + '\n');
                            mscWrite.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of signatures used as records: " + totRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
