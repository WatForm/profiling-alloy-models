package com.alloyprofiling.patternsofuse.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import com.google.common.collect.Sets;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of quantified top, sub and extension signatures in Alloy models
public class Quantification {
    //counter for total number of top-level quantification (over all models)
    private static int totTop = 0;
    //counter for total number of subset quantification (over all models)
    private static int totSub = 0;
    //counter for total number of extension quantification (over all models)
    private static int totExt = 0;
    //counter for total number of set expression quantification (over all models)
    private  static int totSet = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";

        System.out.println("Examining top-level vs. subset level vs. extension level quantification " +
                "in Alloy models in " + path);

        //files containing the number of top, ext, sub and set level quantification
        String fp_quantTop = directoryName + "quantTop.txt";
        String fp_quantSub = directoryName + "quantSub.txt";
        String fp_quantExt = directoryName + "quantExt.txt";
        String fp_quantSet = directoryName + "quantSet.txt";

        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_quantTop));
            Files.deleteIfExists(Paths.get(fp_quantSub));
            Files.deleteIfExists(Paths.get(fp_quantExt));
            Files.deleteIfExists(Paths.get(fp_quantSet));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterating over all models
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
                        Collection<ParseTree> nameTrees =  XPath.findAll(tree, "//decls_e//decl/expr/name", parser);

                        List<String> names = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        //System.out.println("Quantified sets: " + names);

                        //Classifying sig names into top, sub or ext
                        List<String> quantTop = names.stream().filter(top::contains).collect(Collectors.toList());
                        List<String> quantSub = names.stream().filter(subsets::contains).collect(Collectors.toList());
                        List<String> quantExt = names.stream().filter(extensions::contains).collect(Collectors.toList());

                        //extracting total quantification
                        Collection<ParseTree> allQuant =  XPath.findAll(tree, "//decls_e//decl", parser);

                        //getting set quantification by removing all elements in nameTrees (i.e. single set
                        //quantification) from allQuant
                        List<ParseTree> nameDecls = nameTrees.stream().map(ParseTree::getParent).
                                map(ParseTree::getParent).collect(Collectors.toList());

                        List<ParseTree> setQuant_trees = new ArrayList<>(Sets.difference(Sets.newHashSet(allQuant),
                                Sets.newHashSet(nameDecls)));

                        List<String> setQuant = setQuant_trees.stream().map(ParseTree::getText).
                                collect(Collectors.toList());

                        System.out.println("Top quantification: " + quantTop.size() + " " + quantTop);
                        System.out.println("Subset Quantification: " + quantSub.size() + " " + quantSub);
                        System.out.println("Extension Quantification: " + quantExt.size() + " " + quantExt);
                        System.out.println("Set Quantification: " + setQuant.size() + " " + setQuant);


                        //writing quantified top, sub and ext scope counts
                        ResultWriter.writeResults(fp_quantTop, quantTop.size());
                        ResultWriter.writeResults(fp_quantSub, quantSub.size());
                        ResultWriter.writeResults(fp_quantExt, quantExt.size());
                        ResultWriter.writeResults(fp_quantSet, setQuant.size());


                        //Incrementing total number of quantified top, sub and ext scopes
                        totTop += quantTop.size();
                        totSub += quantSub.size();
                        totExt += quantExt.size();
                        totSet += setQuant.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println();
            System.out.println("Total top quantification: " + totTop);
            System.out.println("Total sub quantification: " + totSub);
            System.out.println("Total ext quantification: " + totExt);
            System.out.println("Total set quantification: " + totSet);
            System.out.println("Total quantification: " + (totTop + totSub + totExt + totSet));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
