package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
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

//class that count the number of signatures in Alloy models
public class SignaturesSkipsMods {
    //counter for total number of signatures (across all models)
    private static int totSigCount = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting signature declarations in Alloy models in " + path);

        //file containing the signature count for each model
        String fp_sigUse = directoryName + "sigs.txt";
        String fp_zeroSigs ="Results\\zeroSigs.txt";

        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String source = Files.readString(filePath);

                        ALLOYLexer lexer = new ALLOYLexer(CharStreams.fromString(source));
                        ALLOYParser parser = new ALLOYParser(new CommonTokenStream(lexer));
                        ParseTree tree = parser.specification();

                        System.out.println(filePath.toFile());

                        //pattern to find open commands
                        ParseTreePattern p_open = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches_open = p_open.findAll(tree, "//specification/*");

                        //post-processing to pull out open commands without util"
                        List<ParseTree> modTrees = matches_open.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> userModules = modTrees.stream().map(ParseTree::getText).
                                filter(n-> !(n.contains("util"))).collect(Collectors.toList());

                        //getting all signature names in the model using SigRetriever
                        List<String> totalSigs = SigRetriever.getSigs("all", parser, tree);
                        //getting all enum names in the model using EnumRetriever
                        List<String> enums = EnumRetriever.getEnums("all", parser, tree);

                        //getting signature names from opened user modules

                        if (!userModules.isEmpty()) {
                            for (String module: userModules) {
                                //printing filename of model with imported user-created module
                                System.out.println("Contains user-created module: " + module);
                                try {
                                    String module_str = Files.readString(Path.of(path + "\\" + module + ".als"));
                                    ALLOYLexer lexer_mod = new ALLOYLexer(CharStreams.fromString(module_str));
                                    ALLOYParser parser_mod = new ALLOYParser(new CommonTokenStream(lexer_mod));
                                    ParseTree tree_mod = parser_mod.specification();
                                    totalSigs.addAll(SigRetriever.getSigs("all", parser_mod,tree_mod));
                                } catch (IOException e) {
                                    //skipping files where imported user-module cannot be located
                                    //and signature count is 0
                                    System.out.println("Skipped: " + filePath.toFile());
                                    return;

                                } catch (RecognitionException e) {
                                    e.printStackTrace();
                                }
                            }
                        } 

                        //total number of sets (i.e. sigs + enums)
                        int total = totalSigs.size() + enums.size();

                        //incrementing counter (across all models)
                        totSigCount += totalSigs.size() + enums.size();

                        System.out.println("Number of signatures: " + total + "\n");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Signatures: " + totSigCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
