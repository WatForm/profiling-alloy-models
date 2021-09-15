package com.alloyprofiling.characteristics.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

//class that counts the number of top-level signatures in Alloy models
public class TopLevelSigs {
    //class attribute containing total number of top-level signatures (across all models)
    private static int totTopLevel = 0;

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
        System.out.println("Counting top-level signatures in Alloy models in " + path);

        //file containing number of top-level sigs in each model
        String fp_topSigs = directoryName + "topSigs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_topSigs));
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

                        //getting names of all top-level sigs using SigRetriever
                        List<String> topSigs = SigRetriever.getSigs("top", parser, tree);
                        //getting names of top-level enums using SigRetriever
                        //enum Time [morning, noon, night] --> only Time is top-level
                        List<String> topEnums = EnumRetriever.getEnums("top", parser, tree);

                        //total number of top-levle signatures in a model (i.e. top sig declarations + top enums)
                        int total = topSigs.size() + topEnums.size();

                        totTopLevel += topSigs.size() + topEnums.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of top-level signatures: " + total);

                        //writing to results file
                        ResultWriter.writeResults(fp_topSigs, total);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of top-level Signatures: " + totTopLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
