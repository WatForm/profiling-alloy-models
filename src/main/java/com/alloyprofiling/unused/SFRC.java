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
public class SFRC {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Computing SFRC for Alloy models in " + path);

        //deleting results file if it already exists
        String fp_SFRC = "Results\\QualityIndicators\\SFRC.txt";
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

                        //pattern to find sets under expressions
                        Collection<ParseTree> nameTrees = XPath.findAll(tree, "//expr//name", parser);

                        //getting all signature names
                        List<String> sigNames = SigRetriever.getSigs("all", parser, tree);

                        List<String> sets_strings = nameTrees.stream().map(n -> n.getText()).collect(Collectors.toList());
                        List<String> sets = sets_strings.stream().filter(n -> sigNames.contains(n)).collect(Collectors.toList());

                        System.out.println(filePath.toFile());
                        System.out.println("SFRC: " + sets.size());

                        //writing result file containing the number of sets in all expressions of the model
                        try {
                            FileWriter myWriter = new FileWriter(fp_SFRC, true);
                            myWriter.write(Integer.toString(sets.size()) + '\n');
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
