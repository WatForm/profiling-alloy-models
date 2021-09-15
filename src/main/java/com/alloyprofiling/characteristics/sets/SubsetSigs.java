package com.alloyprofiling.characteristics.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

//class that counts subset signatures in Alloy models
public class SubsetSigs {
    //class attribute containing total number of subset signatures across all models
    private static int totSubsets = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting subset signatures in Alloy models in " + path);

        //file containing number of subset signatures in each model
        String fp_subsetSigs = directoryName + "subSigs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_subsetSigs));
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

                        //extracting the names of subset signatures using SigRetriever
                        List<String> subsets = SigRetriever.getSigs("subsets", parser, tree);

                        System.out.println(filePath.toFile());
                        System.out.println("Subset Signatures: " + subsets.size());
                        totSubsets += subsets.size();

                        //writing result file containing the subset signatures count of each model
                        ResultWriter.writeResults(fp_subsetSigs, subsets.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of subset sigs: " + totSubsets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
