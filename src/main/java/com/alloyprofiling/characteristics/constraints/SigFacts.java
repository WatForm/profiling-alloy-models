package com.alloyprofiling.characteristics.constraints;

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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SigFacts {
    private static int totSigFacts = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Constraints\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting number of signatures with facts in Alloy models in " + path);

        //file containing the number of signatures with facts in each model
        String fp_sigFacts = directoryName + "sigFacts.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_sigFacts));
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

                        //pattern to find signature declarations with a fact block
                        ParseTreePattern p_sigFacts= parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> <sigExtension> { <decls> } <block>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_sigFacts = p_sigFacts.findAll(tree, "//paragraph/*");

                        List<String> matches_str = matches_sigFacts.stream().map(m -> m.get("block").getText().
                                replaceAll("\\s","")).filter(s -> !s.equals("{}")).
                                collect(Collectors.toList());

                        System.out.println("Signatures with fact blocks: " + matches_str.size());

                        System.out.println(filePath.toFile());
                        for (ParseTreeMatch m: matches_sigFacts) {
                            int count = 0;
                            String match_str = m.get("block").getText().replaceAll("\\s","");
                            if (!match_str.equals("{}")) {
                                String[] namesList = m.get("names").getText().split(",");
                                count += namesList.length;
                            }
                            //writing result file containing the number of sigs with facts in each model
                            ResultWriter.writeResults(fp_sigFacts, count);
                            totSigFacts += count;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of sigs with facts: " + totSigFacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
