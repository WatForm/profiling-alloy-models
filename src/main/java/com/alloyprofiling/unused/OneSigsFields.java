package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that computes the number of one signatures with fields in Alloy models
public class OneSigsFields {
    //counter for total number of one sigs with fields across all models
    private static int totOneSigsWithFields = 0;
    private static int totOneSigsWithoutFields = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Signatures\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "database";
        System.out.println("Counting number of one sigs with fields in Alloy models in " + path);

        //file containing the number of one sigs per models
        String fp_oneSigFields = directoryName + "oneSigFields.txt";

        //deleting results file if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_oneSigFields));
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

                        //extracting all signatures with multiplicity one
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> one sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> sigMatches = p.findAll(tree, "//paragraph/*");

                        List<ParseTreeMatch> oneSigsFields = new ArrayList<>();
                        for (ParseTreeMatch m: sigMatches) {
                            Collection<ParseTree> nameTrees = XPath.findAll(m.getTree(), "//sigDecl/names/name", parser);
                            try {
                                List<String> fields = m.getAll("decls").stream().map(ParseTree::getText).
                                        collect(Collectors.toList());
                                fields.removeIf(String::isEmpty);
                                if (!fields.isEmpty())
                                    for (int i=0; i<nameTrees.size(); i++) {
                                        oneSigsFields.add(m);
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        List<String> oneSigsNoFields = SigRetriever.getSigs("onenofields", parser, tree);
                        totOneSigsWithoutFields += oneSigsNoFields.size();


                        totOneSigsWithFields += oneSigsFields.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of One Sigs with Fields: " + oneSigsFields.size());

                        //writing result files containing the number of one sigs and enums in each file
                        ResultWriter.writeResults(fp_oneSigFields, oneSigsFields.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of one sigs with field: " + totOneSigsWithFields);
            System.out.println("Total number of one sigs without field: " + totOneSigsWithoutFields);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
