package com.alloyprofiling.analysiscomplexity.arity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
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

public class FieldArity {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\AnalysisComplexity\\FieldArity\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }
        String path = "corpus";
        System.out.println("Computing Field Arity for Alloy models in " + path);

        //file containing the arity of all relations across all models
        String fp_arity = directoryName + "arity.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_arity));
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

                        //getting all field declarations
                        Collection<ParseTree> declsTrees =  XPath.findAll(tree, "//decls/decl", parser);

                        //pulling out the expr part of the field declaration and accounting for
                        //multiple relations in one declaration
                        List<String> field_exprs = new ArrayList<>();
                        for (ParseTree d: declsTrees) {
                            String[] field_split = d.getText().split(":")[0].split(",");
                            String decl_split = d.getText().split(":")[1];
                            if (field_split.length > 1) {
                                for (String s : field_split) {
                                    field_exprs.add(decl_split);
                                }
                            }
                            else {
                                field_exprs.add(decl_split);
                            }
                        }


                        List<String> exprs = declsTrees.stream().map(i -> i.getText().split(":")).collect(Collectors.toList())
                                .stream().map(i->i[1]).collect(Collectors.toList());

                        //splitting expr based on ->
                        //List<String[]> split_ArrowExpr = exprs.stream().map(e -> e.split("->")).collect(Collectors.toList());
                        List<String[]> split_ArrowExpr = field_exprs.stream().map(e -> e.split("->")).collect(Collectors.toList());

                        //Computing arity and writing result to file
                        System.out.println(filePath.toFile());
                        int index = 0;
                        for(String[] s: split_ArrowExpr) {
                            System.out.println("Arity of " + field_exprs.get(index)  + ": " + (s.length + 1));
                            index++;
                            ResultWriter.writeResults(fp_arity, (s.length +1));
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
