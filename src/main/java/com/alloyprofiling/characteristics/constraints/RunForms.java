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
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of run commands used with names, blocks and named blocks
public class RunForms {
    //counter for total number of run commands
    private static int totRun = 0;
    //counter for total number of run commands used with a named block
    private static int totRunNamedBlocks = 0;
    //counter for total number of run commands used with a name
    private static int totRunName = 0;
    //counter for total number of run commands used with a block
    private static int totRunBlock = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Constraints\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting run commands forms in Alloy models in " + path);

        //file containing number of run commands used with names per model
        String fp_runName = directoryName + "runName.txt";
        //file containing number of run commands used with blocks per model
        String fp_runBlock = directoryName + "runBlock.txt";
        //file containing number of run commands used with named blocks per model
        String fp_runNamedBlock = directoryName + "runNamedBlock.txt";


        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_runName));
            Files.deleteIfExists(Paths.get(fp_runBlock));
            Files.deleteIfExists(Paths.get(fp_runNamedBlock));

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

                        //pattern to find run commands used with names
                        ParseTreePattern p_runName = parser.compileParseTreePattern(
                                "<name_cmd_opt> run <name> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> runNames = p_runName.findAll(tree, "//paragraph/*");

                        /*
                        //extracting constraint container name from run commands
                        List<String> constraintNames = runNames.stream().map(m -> m.get("name")).
                                map(ParseTree::getText).collect(Collectors.toList());

                        //getting names of all predicates using PredicateRetriever
                        List<String> predicates = PredicateRetriever.getPredicates(parser, tree);

                        //post-processing to check if run is used with a predicate
                        List<String> runName = constraintNames.stream().filter(predicates::contains).
                                collect(Collectors.toList()); */

                        //pattern to find run commands used with named blocks
                        ParseTreePattern pattern_runName = parser.compileParseTreePattern(
                                "<name_cmd_opt> run <nameID> <block> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_runName = pattern_runName.findAll(tree, "//paragraph/*");

                        List<String> runNamedBlock = matches_runName.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //pattern to find run commands with blocks
                        ParseTreePattern p_runBlock = parser.compileParseTreePattern(
                                "<name_cmd_opt> run <block> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_runBlock = p_runBlock.findAll(tree, "//paragraph/*");

                        List<String> runBlock = matches_runBlock.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("Run commands used with names: " + runNames.size() + " " + runNames);
                        System.out.println("Run commands used with a named block: " + runNamedBlock.size()+ " " + runNamedBlock);
                        System.out.println("Run commands used with a block: " + runBlock.size()+ " " + runBlock);


                        //incrementing counters
                        totRun += runNamedBlock.size() + runNames.size() + runBlock.size();
                        totRunName += runNames.size();
                        totRunNamedBlocks += runNamedBlock.size();
                        totRunBlock += runBlock.size();

                        //writing result file containing the number of runs used with names in each file
                        ResultWriter.writeResults(fp_runName, runNames.size());
                        //writing result file containing the number of runs used with blocks in each file
                        ResultWriter.writeResults(fp_runBlock, runBlock.size());
                        //writing result file containing the number of runs used with a named block in each file
                        ResultWriter.writeResults(fp_runNamedBlock, runNamedBlock.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of run commands: " + totRun);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            System.out.println("Total run name: " + totRunName + " i.e. " +
                    df.format(totRunName * 100.0 / totRun) +"% of all run commands");
            System.out.println("Total run {constraints}: " + totRunBlock + " i.e. " +
                    df.format(totRunBlock * 100.0 / totRun) +"% of all run commands");
            System.out.println("Total run name {constraints}: " + totRunNamedBlocks + " i.e. " +
                    df.format(totRunNamedBlocks * 100.0 / totRun) +"% of all run commands");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
