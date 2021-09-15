package com.alloyprofiling.characteristics.constraintholders;

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
import java.util.stream.Stream;

public class RunCheck {
    private static int totCmd = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\ConstraintHolders\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "database";
        System.out.println("Counting run vs. check for Alloy models in " + path);

        //file containing number of run commands per model
        String fp_run = directoryName + "run.txt";
        //file containing number of check commands per model
        String fp_check = directoryName + "check.txt";
        //file containing total number of command queries per model
        String fp_cmd = directoryName + "cmd.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_run));
            Files.deleteIfExists(Paths.get(fp_check));
            Files.deleteIfExists(Paths.get(fp_cmd));
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

                        //pattern to find run commands
                        ParseTreePattern p_run = parser.compileParseTreePattern("<name_cmd_opt> run <nameOrBlock> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_run = p_run.findAll(tree, "//paragraph/*");

                        //pattern to find check commands
                        ParseTreePattern p_check = parser.compileParseTreePattern("<name_cmd_opt> check <nameOrBlock> <scope_opt>", ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_check = p_check.findAll(tree, "//paragraph/*");

                        //post-processing to count occurrences of run vs command
                        System.out.println(filePath.toFile());

                        System.out.println("Number of run: " + matches_run.size());
                        System.out.println("Number of check: " + matches_check.size());
                        System.out.println("Number of commands: " + (matches_check.size() + matches_run.size()));

                        totCmd += matches_run.size() + matches_check.size();

                        //writing result file containing the number of run/checks in each file
                        ResultWriter.writeResults(fp_run, matches_run.size());
                        ResultWriter.writeResults(fp_check, matches_check.size());
                        ResultWriter.writeResults(fp_cmd, (matches_run.size() + matches_check.size()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of commands: " + totCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
