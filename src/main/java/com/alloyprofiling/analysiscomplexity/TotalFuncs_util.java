package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TotalFuncs_util {
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Counting util total functions models in " + path);

        //file containing the number of total functions from the util modules per model
        String fp_totalFuncs = "Results\\AnalysisComplexity\\utilTotalFuncs.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_totalFuncs));

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

                        int totTf_count = 0;

                        List<String> var_funcs = Arrays.asList("max[", "min[", "first[", "last[");
                        List<String> var_funcs_noArg = Arrays.asList("max", "min", "first", "last");


                        Collection<ParseTree> exprs = XPath.findAll(tree, "//expr", parser);

                        List<String> used_tf = new ArrayList<>();
                        for (ParseTree t: exprs) {
                            if (t.getParent() instanceof ALLOYParser.BlockContext) {
                                if (!var_funcs.stream().anyMatch(t.getText()::contains)
                                        && var_funcs_noArg.stream().anyMatch(t.getText()::contains)) {
                                    for (String func: var_funcs_noArg){
                                        int tmp_count = StringUtils.countMatches(t.getText(), func);
                                        if (tmp_count > 0)
                                            used_tf.add(func);
                                    }
                                }
                            }
                        }

                        totTf_count+= used_tf.stream().distinct().collect(Collectors.toList()).size();


                        System.out.println(filePath.toFile());
                        //System.out.println(totPf_count);
                        //System.out.println(partFuncs);

                        System.out.println("Number of util total functions: " + totTf_count);

                        try {
                            FileWriter upfWrite = new FileWriter(fp_totalFuncs, true);
                            upfWrite.write(Integer.toString(totTf_count) + '\n');
                            upfWrite.close();
                        } catch (Exception e) {
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
