package com.alloyprofiling.patternsofuse.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alloyprofiling.ResultWriter.writeResults;

public class QuantTopSub {
    private static int totTop = 0;
    private static int totSub = 0;
    private static int totExt = 0;
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Examining top-level vs. subset level quantification " +
                "in Alloy models in " + path);

        //files containing the number of top and sub level quantification
        String fp_quantTop = "Results\\ModelingPractices\\quantTop.txt";
        String fp_quantSub = "Results\\ModelingPractices\\quantSub.txt";
        String fp_quantExt = "Results\\ModelingPractices\\quantExt.txt";


        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_quantTop));
            Files.deleteIfExists(Paths.get(fp_quantSub));
            Files.deleteIfExists(Paths.get(fp_quantExt));
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

                        System.out.println(filePath.toFile());

                        //Signatures

                        //all top-level sigs in the model
                        List<String> topSigs = SigRetriever.getSigs("top", parser, tree);
                        //all subset signatures in a model
                        List<String> subsets = SigRetriever.getSigs("subsets", parser, tree);
                        //all signature extensions in a model
                        List<String> extensions_sig = SigRetriever.getSigs("extensions", parser, tree);

                        //Enums
                        //top-level enums
                        List<String> topEnums = EnumRetriever.getEnums("top", parser, tree);
                        List<String> extEnums = EnumRetriever.getEnums("extensions", parser, tree);

                        //Combining Sigs + Enums
                        List<String> top = Stream.concat(topSigs.stream(), topEnums.stream())
                                .collect(Collectors.toList());
                        List<String> extensions = Stream.concat(extensions_sig.stream(), extEnums.stream())
                                .collect(Collectors.toList());

                        //Extracting all sig names from quantified expressions
                        Collection<ParseTree> nameTrees =  XPath.findAll(tree, "//expr/decls_e/decl/expr/name", parser);

                        List<String> names = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        System.out.println("Quantified sets: " + names);

                        //Classifying sig names into top, sub or ext
                        List<String> quantTop = names.stream().filter(top::contains).collect(Collectors.toList());
                        List<String> quantSub = names.stream().filter(subsets::contains).collect(Collectors.toList());
                        List<String> quantExt = names.stream().filter(extensions::contains).collect(Collectors.toList());


                        System.out.println("Top quantification: " + quantTop.size() + " " + quantTop);
                        System.out.println("Subset Quantification: " + quantSub.size() + " " + quantSub);
                        System.out.println("Extension Quantification: " + quantExt.size() + " " + quantExt);


                        //writing quantified top, sub and ext scope counts
                        writeResults(fp_quantTop, quantTop.size());
                        writeResults(fp_quantSub, quantSub.size());
                        writeResults(fp_quantExt, quantExt.size());


                        //Incrementing total number of quantified top, sub and ext scopes
                        totTop += quantTop.size();
                        totSub += quantSub.size();
                        totExt += quantExt.size();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println();
            System.out.println("Total top quantification: " + totTop);
            System.out.println("Total sub quantification " + totSub);
            System.out.println("Total sub quantification " + totExt);
            System.out.println("Total quantification: " + (totTop + totSub + totExt));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
