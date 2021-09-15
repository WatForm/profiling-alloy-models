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

//class that counts the number of check commands used with names, blocks and named blocks
public class CheckForms {
    //counter for total number of check commands
    private static int totCheck = 0;
    //counter for total number of check commands used with a named block
    private static int totCheckNamedBlocks = 0;
    //counter for total number of check commands used with a name
    private static int totCheckName = 0;
    //counter for total number of check commands used with a block
    private static int totCheckBlock = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Constraints\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting check commands forms in Alloy models in " + path);

        //file containing number of check commands used with names per model
        String fp_checkName = directoryName + "checkName.txt";
        //file containing number of check commands used with blocks per model
        String fp_checkBlock = directoryName + "checkBlock.txt";
        //file containing number of check commands used with named blocks per model
        String fp_checkNamedBlock = directoryName + "checkNamedBlock.txt";


        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_checkName));
            Files.deleteIfExists(Paths.get(fp_checkBlock));
            Files.deleteIfExists(Paths.get(fp_checkNamedBlock));

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

                        //pattern to find check commands used with assertions
                        ParseTreePattern p_checkName = parser.compileParseTreePattern(
                                "<name_cmd_opt> check <name> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> checkNames = p_checkName.findAll(tree, "//paragraph/*");

                        /*
                        //extracting constraint container name from check commands
                        List<String> constraintNames = checkAsserts.stream().map(m -> m.get("name")).
                                map(ParseTree::getText).collect(Collectors.toList());

                        //getting names of all predicates using PredicateRetriever
                        List<String> assertions = AssertionRetriever.getAssertions(parser, tree);

                        //post-processing to check if run is used with a predicate
                        List<String> checkAssert = constraintNames.stream().filter(assertions::contains).
                                collect(Collectors.toList()); */

                        //pattern to find check command used with a named block
                        ParseTreePattern pattern_checkNamedBlock = parser.compileParseTreePattern(
                                "<name_cmd_opt> check <nameID> <block> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_checkName = pattern_checkNamedBlock.findAll(tree, "//paragraph/*");

                        List<String> checkNamedBlock = matches_checkName.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //pattern to find check commands with blocks
                        ParseTreePattern p_checkBlock = parser.compileParseTreePattern(
                                "<name_cmd_opt> check <block> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_checkBlock = p_checkBlock.findAll(tree, "//paragraph/*");

                        List<String> checkBlock = matches_checkBlock.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("Check commands used with assertion names: " + checkNames.size() + " " + checkNames);
                        System.out.println("Check commands used with a named block: " + checkNamedBlock.size()+ " " + checkNamedBlock);
                        System.out.println("Check commands used with a block: " + checkBlock.size()+ " " + checkBlock);


                        //incrementing counters
                        totCheck += checkNamedBlock.size() + checkNames.size() + checkBlock.size();
                        totCheckName += checkNames.size();
                        totCheckNamedBlocks += checkNamedBlock.size();
                        totCheckBlock += checkBlock.size();

                        //writing result file containing the number of check commands used with names in each file
                        ResultWriter.writeResults(fp_checkName, checkNames.size());
                        //writing result file containing the number of check commands used with blocks in each file
                        ResultWriter.writeResults(fp_checkBlock, checkBlock.size());
                        //writing result file containing the number of check commands used with a named block in each file
                        ResultWriter.writeResults(fp_checkNamedBlock, checkNamedBlock.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of check commands: " + totCheck);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            System.out.println("Total check assertion: " + totCheckName + " i.e. " +
                    df.format(totCheckName * 100.0 / totCheck) +"% of all check commands");
            System.out.println("Total check {constraints}: " + totCheckBlock + " i.e. " +
                    df.format(totCheckBlock * 100.0 / totCheck) +"% of all check commands");
            System.out.println("Total check name {constraints}: " + totCheckNamedBlocks + " i.e. " +
                    df.format(totCheckNamedBlocks * 100.0 / totCheck) +"% of all check commands");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
