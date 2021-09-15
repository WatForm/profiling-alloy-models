package com.alloyprofiling.characteristics.length;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

//Computes the length (not including blank lines) of Alloy models in a given repository
public class ModelLength {
    private static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(path);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\CharacteristicsOfModels\\Length\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        //repository of models
        String path = "corpus";
        System.out.println("Counting non-empty lines (excluding comments) for Alloy models in  " + path);

        //file containing the number of lines in each model
        String fp_lines = directoryName + "length.txt";
        String fp_ratio = directoryName + "codeLength.txt";
        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_lines));
            Files.deleteIfExists(Paths.get(fp_ratio));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //iterating over repository of models
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        System.out.println(filePath.toFile());

                        String s = readFile(String.valueOf(filePath));
                        Pattern p = Pattern.compile("/\\*[\\s\\S]*?\\*/|(//)[^\\n\\r]*[\\n\\r]|(--)[^\\n\\r]*[\\n\\r]");

                        Matcher m = p.matcher(s);

                        int comments = 0;
                        while (m.find()) {

                            String lines[] = m.group(0).split("\n");
                            for (String string : lines) {
                                if (!string.isBlank())
                                    comments++;
                            }
                        }

                        //counting non-empty lines in alloy model
                        long count = Files.lines(Paths.get(String.valueOf(filePath))).filter(line -> line.length() > 0).
                                count();
                        System.out.println("Length w/ comments: " + count);
                        System.out.println("Total lines for comments: " + comments);
                        long length = count - comments;
                        System.out.println("Model Length: " + length);

                        //writing results file containing the number of lines in each model
                        try {
                            FileWriter lengthWriter = new FileWriter(fp_lines, true);
                            lengthWriter.write(Long.toString(length) + '\n');
                            lengthWriter.close();
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                        if (length != 0) {
                            try {
                                FileWriter ratioWriter = new FileWriter(fp_ratio, true);
                                ratioWriter.write(Double.toString((double) comments / length) + '\n');
                                ratioWriter.close();
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println("Zero length: " + filePath.toFile());
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

    public static long getLength(String path) throws IOException {
        String s = readFile(path);
        Pattern p = Pattern.compile("/\\*[\\s\\S]*?\\*/|(//)[^\\n\\r]*[\\n\\r]|(--)[^\\n\\r]*[\\n\\r]");

        Matcher m = p.matcher(s);

        int total = 0;
        while (m.find()) {

            String lines[] = m.group(0).split("\n");
            for (String string : lines) {
                if (!string.isBlank())
                    total++;
            }
        }

        //counting non-empty lines in alloy model
        long count = Files.lines(Paths.get(path)).filter(line -> line.length() > 0).
                count();
        long length = count - total;

        return length;
    }
}
