package com.alloyprofiling.characteristics.signatures;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
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

//class that counts the number of subsignature extensions in Alloy models
public class ExtensionSigs {
    //counter for total number of subsignature extensions (across all models)
    private static int totExt = 0;
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
        System.out.println("Counting subsignature extensions in Alloy models in " + path);

        //file containing the number of extension signatures in each model
        String fp_extSigs = directoryName + "extSigs.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_extSigs));
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

                        //extracting the names of subsignature extensions using SigRetriever
                        List<String> extensionSigs = SigRetriever.getSigs("extensions", parser, tree);

                        //extracting enum extension using EnumRetriever
                        List<String> extensionEnums = EnumRetriever.getEnums("extensions", parser, tree);

                        System.out.println(filePath.toFile());
                        System.out.println("Subsignature extensions: " + extensionSigs.size());
                        System.out.println("Enum extensions: " + extensionEnums.size());
                        totExt += extensionSigs.size() + extensionEnums.size();

                        //writing result file containing number of subsignature extensions in each file
                        ResultWriter.writeResults(fp_extSigs, (extensionSigs.size() + extensionEnums.size()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of sig extensions: " + totExt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
