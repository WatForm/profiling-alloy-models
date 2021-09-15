package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Counts the number of opened user-created modules in Alloy models
public class ModelSpan {
    private static int totMod = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\LengthAndSpan\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        //repository of models
        String path = "database";
        System.out.println("Counting opened user modules in Alloy models in " + path);

        String fp_userModules = directoryName + "span.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_userModules));

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

                        //XPath hierarchy path to identify "module" commands
                        Collection<ParseTree> moduleTrees = XPath.findAll(tree, "//module", parser);

                        //skipping files that are declared as modules
                        if (!moduleTrees.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            //incrementing counter
                            totMod += 1;
                            return;
                        }

                        //pattern to find open commands
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");

                        //post-processing to pull out open commands without util
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> moduleNames = allNames.stream().map(ParseTree::getText).collect(Collectors.toList());
                        List<String> userModules = moduleNames.stream().filter(n-> !(n.contains("util"))).collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("User-created modules: " + userModules.size());

                        //writing result file containing the number of opened user-created modules in each file
                        ResultWriter.writeResults(fp_userModules, userModules.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total modules skipped: " + totMod);
    }
}
