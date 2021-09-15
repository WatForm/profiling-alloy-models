package com.alloyprofiling.characteristics.lengthspan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Computes the length (not including blank lines) of Alloy models in a given repository
public class ModelLength {
    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\LengthAndSpan\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "database";
        System.out.println("Counting non-empty lines for Alloy models in  " + path);

        //file containing the number of lines in each model
        String fp_lines = directoryName + "length.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_lines));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        //counting non-empty lines in alloy model
                        long count = Files.lines(Paths.get(String.valueOf(filePath))).filter(line -> line.length() > 0).
                                count();
                        System.out.println(filePath.toFile());
                        System.out.println("Model Length: " + count);

                        //writing results file containing the number of lines in each model
                        try {
                            FileWriter lengthWriter = new FileWriter(fp_lines, true);
                            lengthWriter.write(Long.toString(count) + '\n');
                            lengthWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
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
