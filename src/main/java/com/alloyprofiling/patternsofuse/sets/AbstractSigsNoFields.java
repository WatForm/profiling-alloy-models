package com.alloyprofiling.patternsofuse.sets;

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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of abstract signatures with no children in Alloy models
public class AbstractSigsNoFields {
    //counter for total number of abstract children with no children (over all models)
    private static int totalAbsNoFields = 0;
    private static  int totAbsFields = 0;
    private static int totAbs = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting abstract sigs with no fields in Alloy models in " + path);

        //file containing the number of fields under abstract sigs in each model
        String fp_absFields = directoryName + "absFields.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_absFields));
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

                        List<String> absSignatures = SigRetriever.getSigs("abstract", parser, tree);
                        totAbs += absSignatures.size();

                        //pattern to find abstract signatures
                        ParseTreePattern p_abstract = parser.compileParseTreePattern("<priv> abstract sig <names> <sigExtension> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_abstract = p_abstract.findAll(tree, "//paragraph/*");

                        int abs_no_fields = 0;
                        //iterating over signature declaration parse trees
                        for (ParseTreeMatch sig: matches_abstract) {
                            String names = sig.get("names").getText();
                            String[] namesList = names.split(",");
                            //XPath string to extract all field trees under signature declarations
                            List<String> fields = XPath.findAll(sig.getTree(), "//sigDecl/decls/decl", parser).stream().
                                    map(ParseTree::getText).collect(Collectors.toList());
                            fields.removeIf(String::isEmpty);
                            if (!fields.isEmpty()) {
                                abs_no_fields += namesList.length;
                                totAbsFields += namesList.length;
                                //Counting fields (and accounting for multiple field declarations grouped in one declaration
                                //e.g. f1, f2: A counts as 2)
                                List<Integer> fieldCounts = fields.stream().map(f -> f.split(":")[0])
                                        .map(n -> n.split(",").length).collect(Collectors.toList());

                                //summing all field counts to get the total number of fields under each signature
                                int field_count = fieldCounts.stream().mapToInt(Integer::intValue).sum();
                                ResultWriter.writeResults(fp_absFields, field_count);
                            }
                            else
                                totalAbsNoFields += namesList.length;
                        }


                        System.out.println(filePath.toFile());
                        System.out.println("Number of Abstract Sigs with no fields: " + abs_no_fields);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Abstract Signatures without fields: " + totalAbsNoFields);
            System.out.println("Total number of Abstract Signatures with fields: " + totAbsFields);
            System.out.println("Total number of Abstract Signatures: " + totAbs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
