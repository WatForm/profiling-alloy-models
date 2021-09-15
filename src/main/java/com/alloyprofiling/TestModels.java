package com.alloyprofiling;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestModels {
    static int count = 1;
    //script used to test models
    //generates file containing the names of models that do not successfully parse with the ALLOYParser
    //generates file containing the names of models that have bad encoding
    public static void main(String[] args) {
        String path = "corpus";
        System.out.println("Checking Alloy models in " + path);

        String fp_badfile = "Results\\badModels.txt";
        String fp_encoding = "Results\\badEncoding.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_badfile));
            Files.deleteIfExists(Paths.get(fp_encoding));
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

                        parser.removeErrorListeners();
                        parser.setErrorHandler(new BailErrorStrategy());
                        parser.setBuildParseTree(false);

                        try {
                            System.out.println(count);
                            count++;
                            System.out.println(filePath.toFile());
                            ParseTree tree = parser.specification();

                            if (parser.getNumberOfSyntaxErrors() >= 1) {
                                try {
                                    FileWriter myWriter = new FileWriter(fp_badfile, true);
                                    System.out.println("Bad file: " + filePath);
                                    myWriter.write( filePath.toString() + '\n');
                                    myWriter.close();
                                } catch (IOException exc) {
                                    System.out.println("An error writing files occurred.");
                                    exc.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            //writing result file containing the number of bad files
                            try {
                                FileWriter myWriter = new FileWriter(fp_badfile, true);
                                System.out.println("Bad file: " + filePath);
                                myWriter.write( filePath.toString() + '\n');
                                myWriter.close();
                            } catch (IOException exc) {
                                System.out.println("An error writing files occurred.");
                                exc.printStackTrace();
                            }
                        }

                    //writing result file containing the number of files with bad encoding
                    } catch (Exception e) {
                        try {
                            FileWriter encWriter = new FileWriter(fp_encoding, true);
                            System.out.println("Bad encoding: " + filePath.toString());
                            encWriter.write( filePath.toString() + '\n');
                            encWriter.close();
                        } catch (IOException exc) {
                            System.out.println("An error writing files occurred.");
                            exc.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
