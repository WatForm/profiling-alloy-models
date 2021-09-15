package com.alloyprofiling.patternsofuse.scopes;

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

import static com.alloyprofiling.ResultWriter.writeResults;

public class IntegerScopes {
    private static int totQuery = 0;
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Scopes\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting Int/int Scopes in Alloy models in " + path);

        String fp_integerScopes = directoryName + "intScopes.txt";
        String fp_intValues = directoryName + "intScopeNums.txt";


        //deleting result file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_integerScopes));
            Files.deleteIfExists(Paths.get(fp_intValues));

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

                        //XPath string to find all command queries
                        Collection<ParseTree> nameTrees =  XPath.findAll(tree,
                                "//typescopes//typescope//name", parser);

                        //Extracting all "Int"/"int" occurrences from command queries (typescope part of them)
                        ParseTreePattern p_int = parser.compileParseTreePattern
                                ("<exactly_opt> <number> int", ALLOYParser.RULE_typescope);
                        List<ParseTreeMatch> matches_int = p_int.findAll(tree, "//typescopes/*");

                        ParseTreePattern p_Int = parser.compileParseTreePattern
                                ("<exactly_opt> <number> Int", ALLOYParser.RULE_typescope);
                        List<ParseTreeMatch> matches_Int = p_Int.findAll(tree, "//typescopes/*");

                        List<ParseTreeMatch> matches = Stream.concat(matches_int.stream(), matches_Int.stream())
                                .collect(Collectors.toList());

                        List<Integer> int_scopes = matches.stream().map(m -> Integer.parseInt(m.get("number").getText()))
                                .collect(Collectors.toList());

                        int_scopes.forEach(i -> ResultWriter.writeResults(fp_intValues, i));

                        List<String> cmdInts = matches.stream().
                                map(m -> m.getTree().getText())
                                .collect(Collectors.toList());

                        System.out.println(filePath.toFile());

                        System.out.println("Integer Scopes: " + cmdInts.size() + " " +cmdInts);
                        System.out.println("Int Scope Numbers: " + int_scopes);

                        writeResults(fp_integerScopes, cmdInts.size());

                        totQuery += nameTrees.size() + matches_int.size() - matches_Int.size();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of queries: " + totQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
