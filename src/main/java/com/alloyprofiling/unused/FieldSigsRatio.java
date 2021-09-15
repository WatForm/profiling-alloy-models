package com.alloyprofiling.unused;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.RelationRetriever;
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
public class FieldSigsRatio {
    //counter for total number of signatures (across all models)

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
        String fp_fieldSigs = directoryName + "fieldSigsRatio.txt";

        //deleting results file if it already exist
        try {
            Files.deleteIfExists(Paths.get(fp_fieldSigs));
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

                        List<String> fields = RelationRetriever.getRelations(parser,tree);

                        System.out.println("Number of signatures: " + total + "\n");
                        System.out.println("Number of fields: " + fields.size() + "\n");
                        if (total != 0) {
                            Double ratio = (double) fields.size() / total;
                            System.out.println("Ratio of fields to sigs: " +  ratio + "\n");
                            try {
                                FileWriter lengthWriter = new FileWriter(fp_fieldSigs, true);
                                lengthWriter.write(Double.toString(ratio) + '\n');
                                lengthWriter.close();
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
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
