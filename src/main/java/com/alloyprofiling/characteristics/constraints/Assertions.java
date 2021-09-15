package com.alloyprofiling.characteristics.constraints;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.AssertionRetriever;
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

public class Assertions {
    //class attribute containing the total number of asserts across all models
    private static int totAsserts = 0;
    //class attribute containing the total number of assert calls across all models
    private static int totAC = 0;
    //class attribute containing number of assertions without a name across all models
    private static int totAssertNoName = 0;
    //class attribute containing number of models that have assertions
    private  static  int totAssertModels = 0;


    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Constraints\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting assert decls/calls in Alloy models in " + path);

        //file containing the number of assertion declarations in each model
        String fp_assertions = directoryName + "assertDecls.txt";
        //file containing the number of assertion calls in each model
        String fp_assertUses = directoryName + "assertUses.txt";

        //deleting result files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_assertUses));
            Files.deleteIfExists(Paths.get(fp_assertions));
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

                        //pattern to find assertions declared without a name
                        ParseTreePattern pattern_noName = parser.compileParseTreePattern(
                                "assert <block>", ALLOYParser.RULE_assertDecl);
                        List<ParseTreeMatch> matches_noName = pattern_noName.findAll(tree, "//paragraph/*");

                        List<String> assertNoNames = matches_noName.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //incrementing counter for asserts without a name
                        totAssertNoName += assertNoNames.size();

                        //XPath string to extract assert declaration parse trees
                        Collection<ParseTree> assertTrees = XPath.findAll(tree, "//assertDecl", parser);

                        //getting all assertion names from the model using AssertionRetriever
                        List<String> assertions = AssertionRetriever.getAssertions(parser, tree);

                        //pattern to find assertion names under command declarations
                        Collection<ParseTree> nameTrees = XPath.findAll(tree, "//cmdDecl/nameOrBlock/name",
                                parser);
                        List<String> cmdNames = nameTrees.stream().map(n -> n.getText()).collect(Collectors.toList());

                        //filter checks if the collected names are assertions
                        List<String> assertUses = cmdNames.stream().
                                filter(assertions::contains).collect(Collectors.toList());


                        //printing results
                        System.out.println(filePath.toFile());
                        System.out.println("Number of assertion declarations: " + assertTrees.size());
                        System.out.println("Assertion uses: " + assertUses.size());
                        System.out.println("Assertions declared without a name: " + assertNoNames.size());

                        //incrementing total assertion declarations counter
                        totAsserts += assertTrees.size();
                        //incrementing total assertion uses counter
                        totAC += assertUses.size();

                        //incrementing counter for number of models that use assertions
                        if (assertTrees.size() > 0)
                            totAssertModels++;

                        //writing result file containing the number of assertion declarations in each file
                        ResultWriter.writeResults(fp_assertions, assertTrees.size());
                        //writing result file containing the number of assertion uses in each file
                        ResultWriter.writeResults(fp_assertUses, assertUses.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of assertion declarations: " + totAsserts);
            System.out.println("Total number of assertions uses: " + totAC);
            System.out.println("Total number of assertions declared without a name: " + totAssertNoName);
            System.out.println("Models that use assertions: " + totAssertModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
