package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegerLib {
    public static void main(String[] args) {

        String path = "alloy_models";
        System.out.println("Counting uses of Int in Alloy models in " + path);

        //file containing the number of "open util/integer" per model
        String fp_integerMod = "Results\\ModelingPractices\\integerMod.txt";
        //file containing the number of integer constants per model
        String fp_integerConst = "Results\\ModelingPractices\\integerConst.txt";
        //file containing the number of integer relations containing Int fields
        String fp_integerRel = "Results\\ModelingPractices\\integerRel.txt";

        //deleting results file if it already exists

        try {
            Files.deleteIfExists(Paths.get(fp_integerMod));
            Files.deleteIfExists(Paths.get(fp_integerConst));
            Files.deleteIfExists(Paths.get(fp_integerRel));
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

                        //XPath string to find num constants in expressions
                        Collection<ParseTree> numTrees =  XPath.findAll(tree, "//expr/constant/num", parser);

                        //pattern to find field declarations with Int
                        ParseTreePattern p_intField = parser.compileParseTreePattern("<priv> <disjoint> <names> : <disj> Int <comma_opt>", ALLOYParser.RULE_decl);
                        List<ParseTreeMatch> matches_intField = p_intField.findAll(tree, "//sigDecl/decls/*");

                        List<String> intFields = matches_intField.stream().map(m -> m.getTree().getText()).
                                collect(Collectors.toList());

                        //post-processing to pull out all field names i.e. f1,f2: Int counts as 2
                        List<String> intField_names = new ArrayList<>();
                        List<ParseTree> nameTrees = matches_intField.stream().map(match -> match.get("names")).collect(Collectors.toList());
                        List<String> fields = nameTrees.stream().map(ParseTree::getText).collect(Collectors.toList());
                        for (String names : fields) {
                            String[] namesList = names.split(",");
                            Collections.addAll(intField_names, namesList);
                        }

                        System.out.println(filePath.toFile());

                        System.out.println("IntegerMod: " + intModules.size());
                        System.out.println("Number constants: " + numTrees.size());
                        System.out.println("Integer sigs: " + intField_names.size());

                        //writing result file containing the number of integer module uses
                        try {
                            FileWriter writerMod = new FileWriter(fp_integerMod, true);
                            writerMod.write(Integer.toString(intModules.size()) + '\n');
                            writerMod.close();

                            FileWriter writerConst = new FileWriter(fp_integerConst, true);
                            writerConst.write(Integer.toString(numTrees.size()) + '\n');
                            writerConst.close();

                            FileWriter writerSig = new FileWriter(fp_integerRel, true);
                            writerSig.write(Integer.toString(intField_names.size()) + '\n');
                            writerSig.close();

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
