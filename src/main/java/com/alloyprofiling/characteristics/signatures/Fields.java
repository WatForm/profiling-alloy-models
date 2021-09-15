package com.alloyprofiling.characteristics.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
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

public class Fields {
    //class attribute representing the total number of fields across all models
    private static int totFields = 0;
    //class attribute representing the total number of signatures without fields across all models
    private static int totSigsNoFields =0;
    private static int totSigsFields =0;


    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Signatures\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting the number of fields in Alloy models in " + path);

        //file containing the number of fields in each model
        String fp_fields = directoryName + "fields.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_fields));
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

                        //signatures that do not have fields
                        List<String> sigsNoFields = SigRetriever.getSigs("nofields", parser, tree);

                        //incrementing counter for signatures without fields
                        totSigsNoFields += sigsNoFields.size();

                        //XPath hierarchy path to extract all signature declarations
                        Collection<ParseTree> sigTrees = XPath.findAll(tree, "//sigDecl", parser);

                        System.out.println(filePath.toFile());

                        //iterating over signature declaration parse trees
                        for (ParseTree sig: sigTrees) {
                            //XPath string to extract all field trees under signature declarations
                            List<String> fields = XPath.findAll(sig, "//sigDecl/decls/decl", parser).stream().
                                    map(ParseTree::getText).collect(Collectors.toList());
                            fields.removeIf(String::isEmpty);
                            if (!fields.isEmpty()) {
                                //Counting fields (and accounting for multiple field declarations grouped in one declaration
                                //e.g. f1, f2: A counts as 2)
                                List<Integer> fieldCounts = fields.stream().map(f -> f.split(":")[0])
                                        .map(n -> n.split(",").length).collect(Collectors.toList());

                                Collection<ParseTree> nameTrees = XPath.findAll(sig, "//sigDecl/names/name", parser);

                                //summing all field counts to get the total number of fields under each signature
                                int field_count = fieldCounts.stream().mapToInt(Integer::intValue).sum();

                                for (ParseTree name: nameTrees) {
                                    System.out.println("Number of fields under " + name.getText() + ": " + field_count);
                                    //writing result file containing the number of fields in each file
                                    ResultWriter.writeResults(fp_fields, field_count);
                                    totFields += field_count;
                                    totSigsFields++;
                                }
                            }
                        }
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of fields: " + totFields);
            System.out.println("Total number of signatures with fields: " + totSigsFields);
            System.out.println("Total number of signatures without fields: " + totSigsNoFields);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
