package com.alloyprofiling.patternsofuse.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.RelationRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of sigs used as structures in Alloy models
public class SigsAsStructures {
    //counter for total number of signatures used as structures (across all models)
    private static int totStructures = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";

        System.out.println("Counting signatures used as structures in Alloy models in " + path);

        //file containing the number of signatures used as records per model
        String fp_structures = directoryName + "structures.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_structures));
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


                        //map between signature names and their correspondent fields
                        LinkedHashMap<String, List<String>> relMap = RelationRetriever.getSigRelMap(parser, tree);

                        //printing each signature and its fields
                        relMap.entrySet().forEach(entry-> System.out.println("Signature: " +
                                entry.getKey() + " Fields: " + entry.getValue()));

                        //getting all signature names
                        List<String> totalSigs = SigRetriever.getSigs("all", parser, tree);

                        List<String> structures = new ArrayList<>();

                        for (String sig: totalSigs) {
                            //list containing sig's fields
                            List<String> fields = relMap.get(sig);
                            if (fields != null && !fields.isEmpty())
                            {
                                //pattern to find dot joins that bind sig and one of its fields
                                ParseTreePattern p_dot = parser.compileParseTreePattern(sig + " <dotOp> <expr>", ALLOYParser.RULE_expr);
                                //excluding dot joins that have transitive closure operators
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

                                //checking if sig is used as structure
                                if (!matches_sigs_expr.isEmpty() && matches_sigs_expr.size() == (matches_dot.size() + matches_boxJ.size()))
                                    structures.add(sig);
                            }

                        }

                        System.out.println(filePath.toFile());

                        //incrementing counter
                        totStructures += structures.size();
                        System.out.println("Signatures used as structures: " + structures.size());
                        System.out.println(structures);

                        //writing to results file
                        ResultWriter.writeResults(fp_structures, structures.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //printing total number of signatures used as structures (over all models)
            System.out.println("Total number of signatures used as structures: " + totStructures);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
