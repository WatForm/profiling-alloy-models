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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that counts the number of abstract signatures with no children in Alloy models
public class AbstractSigsNoChildren {
    //counter for total number of abstract children with no children (over all models)
    private static int totalAbsNoChild = 0;
    private static  int totMod = 0;

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
        System.out.println("Counting abstract sigs with no children in Alloy models in " + path);

        //file containing the number of abstract sigs with no children in each model
        String fp_absNoChild = directoryName + "absSigsNoChild.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_absNoChild));
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
                        /*
                        if (!moduleTrees.isEmpty()) {
                            System.out.println("Skipped: " + filePath.toFile());
                            //incrementing counter
                            totMod += 1;
                            return;
                        } */

                        //pattern to find child signatures
                        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

                        //getting names of parent sigs
                        List<ParseTree> allNames_parent = matches_children.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> parentSigs = allNames_parent.stream().map(ParseTree::getText).collect(Collectors.toList());

                        //extracting the names of abstract signatures
                        List<String> absSigs = SigRetriever.getSigs("abstract", parser, tree);

                        //filtering out abstract sigs with no children
                        List<String> absNoChildren = new ArrayList<>();
                        for(String absSig : absSigs) {
                            if (!parentSigs.contains(absSig)) {
                                absNoChildren.add(absSig);
                            }
                        }

                        System.out.println(filePath.toFile());
                        System.out.println("Number of Abstract Sigs with no children: " + absNoChildren.size());

                        totalAbsNoChild += absNoChildren.size();

                        //writing result file containing number of abstract sigs with no children
                        ResultWriter.writeResults(fp_absNoChild, absNoChildren.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Abstract Signatures without children: " + totalAbsNoChild);
            System.out.println("Total number of skipped modules: " + totMod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
