package com.alloyprofiling.characteristics.signatures;

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

//class that counts the number of abstract signatures in Alloy models
public class AbstractSigs {
    //counter for total number of abstract signatures (across all models)
    private static int totalAbsCount = 0;
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
        System.out.println("Counting abstract sigs in Alloy models in " + path);

        //file containing the number of abstract sigs in each model
        String fp_absSigs = directoryName + "absSigs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_absSigs));
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

                        List<String> absSigs = SigRetriever.getSigs("abstract", parser, tree);

                        System.out.println(filePath.toFile());
                        totalAbsCount += absSigs.size();
                        System.out.println("Number of Abstract Sigs: " + absSigs.size());

                        //writing result files containing number of abstract sigs in each model
                        ResultWriter.writeResults(fp_absSigs, absSigs.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Abstract Signatures: " + totalAbsCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
