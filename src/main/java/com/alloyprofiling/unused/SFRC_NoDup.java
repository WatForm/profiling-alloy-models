package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// SCRIPT NOT USED
// FSC deemed a better measure
public class SFRC_NoDup {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing SFRC for Alloy models in " + path);

        //deleting results file if it already exists
        String fp_SFRC = "Results\\QualityIndicators\\SFRC_NoDup.txt";
        try {
            Files.deleteIfExists(Paths.get(fp_SFRC));
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

                        //getting all signature names
                        List<String> sigNames = SigRetriever.getSigs("all", parser, tree);

                        //pattern to find sets under expressions
                        Collection<ParseTree> exprTrees = XPath.findAll(tree, "//expr", parser);

                        int set_count = 0;

                        for (ParseTree t: exprTrees) {
                            Collection<ParseTree> nameTrees = XPath.findAll(t, "//name", parser);
                            List<String> name_strings = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                            //filtering out expressions that do not have set names in them
                            if (nameTrees.size() != 0 && sigNames.stream().anyMatch(t.getText()::contains)) {
                                //grabbing top-level expressions
                                if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                    List<String> sets = name_strings.stream().filter(n -> sigNames.contains(n)).
                                            distinct().collect(Collectors.toList());
                                    set_count += sets.size();
                                }
                            }
                        }
                        System.out.println(filePath.toFile());
                        System.out.println("SFRC: " + set_count);

                        //writing result file containing the number of sets in all expressions of the model
                        try {
                            FileWriter myWriter = new FileWriter(fp_SFRC, true);
                            myWriter.write(Integer.toString(set_count) + '\n');
                            myWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error writing files occurred.");
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
