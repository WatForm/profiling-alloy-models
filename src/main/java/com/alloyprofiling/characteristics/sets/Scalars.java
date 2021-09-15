package com.alloyprofiling.characteristics.sets;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.ResultWriter;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class that computes the number of scalars in Alloy models
public class Scalars {
    //counter for total number of one sigs across all models
    private static int totOneCount = 0;
    //counter for total numebr of enums across all models
    private static int totEnum = 0;

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Sets\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "database";
        System.out.println("Counting scalars in Alloy models in " + path);

        //file containing the number of one sigs per models
        String fp_oneSig = directoryName + "oneSigs.txt";
        //file containing number of enum (extensions) per model
        String fp_enum = directoryName + "enums.txt";

        //deleting results file if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_oneSig));
            Files.deleteIfExists(Paths.get(fp_enum));
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

                        //extracting all signatures with multiplicity one using SigRetriever
                        List<String> oneSigs = SigRetriever.getSigs("one", parser, tree);

                        //calling EnumRetriever to get enum (extensions)
                        List<String> enumList = EnumRetriever.getEnums("extensions", parser, tree);

                        //incrementing counters
                        totOneCount += oneSigs.size();
                        totEnum += enumList.size();
                        System.out.println(filePath.toFile());
                        System.out.println("Number of One Sigs: " + oneSigs.size());
                        System.out.println("Number of Enums: " + enumList.size());

                        //writing result files containing the number of one sigs and enums in each file
                        ResultWriter.writeResults(fp_oneSig, oneSigs.size());
                        ResultWriter.writeResults(fp_enum, enumList.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total number of one sigs: " + totOneCount);
            System.out.println("Total number of enums: " + totEnum);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
