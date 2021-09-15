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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

//class that count the number of signatures in Alloy models
public class Signatures {
    //counter for total number of signatures (across all models)
    private static int totSigCount = 0;

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
        System.out.println("Counting signature declarations in Alloy models in " + path);

        //file containing the signature count for each model
        String fp_sigUse = directoryName + "sigs.txt";
        String fp_zeroSigs ="Results\\zeroSigs.txt";

        //deleting results file if it already exist
        try {
            Files.deleteIfExists(Paths.get(fp_sigUse));
            Files.deleteIfExists(Paths.get(fp_zeroSigs));
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

                        System.out.println(filePath.toFile());

                        //getting all signature names in the model using SigRetriever
                        List<String> totalSigs = SigRetriever.getSigs("all", parser, tree);
                        //getting all enum names in the model using EnumRetriever
                        List<String> enums = EnumRetriever.getEnums("all", parser, tree);

                        //total number of sets (i.e. sigs + enums)
                        int total = totalSigs.size() + enums.size();

                        //incrementing counter (across all models)
                        totSigCount += totalSigs.size() + enums.size();

                        System.out.println("Number of signatures: " + total + "\n");

                        if (total == 0) {
                            //writing to result file
                            try {
                                FileWriter zeroWriter = new FileWriter(fp_zeroSigs, true);
                                zeroWriter.write(filePath.toFile().toString()  + '\n');
                                zeroWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //writing to result file containing total number of signatures in each model
                        ResultWriter.writeResults(fp_sigUse, total);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of Signatures: " + totSigCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
