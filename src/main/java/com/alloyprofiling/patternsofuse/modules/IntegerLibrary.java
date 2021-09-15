package com.alloyprofiling.patternsofuse.modules;

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

//class that counts the number of models that open the integer module and use integer without importing
//the integer module
public class IntegerLibrary {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Modules\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Counting uses of Int in Alloy models in " + path);

        //file containing number of models that use integers but do not import util/integer
        String fp_integerNoMod = directoryName + "integer_noMod.txt";
        //file containing number of models that open the integer module
        String fp_integerMod = directoryName + "integer_mod.txt";

        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_integerNoMod));
            Files.deleteIfExists(Paths.get(fp_integerMod));
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

                        //pattern to find open commands
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");

                        //post-processing to extract modules that contain "util/int"
                        List<ParseTree> allNames = matches.stream().map(match -> match.get("name")).collect(Collectors.toList());
                        List<String> intModules = allNames.stream().map(ParseTree::getText).collect(Collectors.toList()).
                                stream().filter(n-> n.contains("util/integer")).collect(Collectors.toList());

                        //counting models that open integer module
                        if (!intModules.isEmpty()){
                            System.out.println("Opens integer module: " + filePath.toFile());
                            ResultWriter.writeResults(fp_integerMod, 1);
                            return;
                        }

                        //XPath hierarchy path to find num constants in expressions
                        Collection<ParseTree> numTrees =  XPath.findAll(tree, "//expr/constant/num", parser);

                        //collecting all integer atoms used in relations
                        Collection<ParseTree> declNames =  XPath.findAll(tree, "//sigDecl//decls//decl//expr//name", parser);
                        List<String> intDecls = declNames.stream().map(ParseTree::getText).
                                filter(d -> d.equalsIgnoreCase("int")).collect(Collectors.toList());


                        System.out.println(filePath.toFile());

                        //counting models that use integers without opening the integer module
                        if (!numTrees.isEmpty() || !intDecls.isEmpty()) {
                            System.out.println("Uses integers without Integer module");
                            ResultWriter.writeResults(fp_integerNoMod, 1);
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
