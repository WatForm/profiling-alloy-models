package com.alloyprofiling.patternsofuse.scopes;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.EnumRetriever;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that iterates over a repository of Alloy models and tallies up the number of scopes set
 * at the query (command), signature, and overall levels.
 */
public class ScopeLevels {
    private static int totTop_cmd = 0; //total number of top scopes at query level (across all models)
    private static int totSub_cmd = 0; //total number of sub scopes at query level (across all models)
    private static int totExt_cmd = 0; //total number of ext scopes at query level (across all models)
    private static int totTop_sig = 0; //total number of top scopes at sig level (across all models)
    private static int totSub_sig = 0; //total number of sub scopes at sig level (across all models)
    private static int totExt_sig = 0; //total number of ext scopes at sig level (across all models)
    /**
     * Consumes a text file path and an integer value. Writes the integer value to the text file
     * @param filepath  string that points to the text file
     * @param value     integer value to be written to the file
     */
    private static void writeResults(String filepath, int value) {
        try {
            FileWriter myWriter = new FileWriter(filepath, true);
            myWriter.write(Integer.toString(value) + '\n');
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error writing files occurred.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String path = "alloy_models";
        System.out.println("Examining top-level vs. subset level scope " +
                "designation in Alloy models in " + path);

        //files containing the number of top and sub level scopes at different levels
        String fp_cmdScopeTop = "Results\\ModelingPractices\\cmdScopeTop.txt";
        String fp_cmdScopeSub = "Results\\ModelingPractices\\cmdScopeSub.txt";
        String fp_cmdScopeExt = "Results\\ModelingPractices\\cmdScopeExt.txt";

        String fp_oneTop = "Results\\ModelingPractices\\oneTop.txt";
        String fp_oneSub = "Results\\ModelingPractices\\oneSub.txt";
        String fp_oneExt = "Results\\ModelingPractices\\oneExt.txt";

        String fp_loneTop = "Results\\ModelingPractices\\loneTop.txt";
        String fp_loneSub = "Results\\ModelingPractices\\loneSub.txt";
        String fp_loneExt = "Results\\ModelingPractices\\loneExt.txt";

        String fp_someTop = "Results\\ModelingPractices\\someTop.txt";
        String fp_someSub = "Results\\ModelingPractices\\someSub.txt";
        String fp_someExt = "Results\\ModelingPractices\\someExt.txt";

        String fp_sigTop = "Results\\ModelingPractices\\sigTop.txt";
        String fp_sigSub = "Results\\ModelingPractices\\sigSub.txt";
        String fp_sigExt = "Results\\ModelingPractices\\sigExt.txt";

        String fp_scopeTop = "Results\\ModelingPractices\\scopeTop.txt";
        String fp_scopeSub = "Results\\ModelingPractices\\scopeSub.txt";
        String fp_scopeExt = "Results\\ModelingPractices\\scopeExt.txt";

        //deleting results files if they already exist
        try {
            Files.deleteIfExists(Paths.get(fp_cmdScopeTop));
            Files.deleteIfExists(Paths.get(fp_cmdScopeSub));
            Files.deleteIfExists(Paths.get(fp_cmdScopeExt));

            Files.deleteIfExists(Paths.get(fp_oneTop));
            Files.deleteIfExists(Paths.get(fp_oneSub));
            Files.deleteIfExists(Paths.get(fp_oneExt));

            Files.deleteIfExists(Paths.get(fp_loneTop));
            Files.deleteIfExists(Paths.get(fp_loneSub));
            Files.deleteIfExists(Paths.get(fp_loneExt));

            Files.deleteIfExists(Paths.get(fp_someTop));
            Files.deleteIfExists(Paths.get(fp_someSub));
            Files.deleteIfExists(Paths.get(fp_someExt));

            Files.deleteIfExists(Paths.get(fp_sigTop));
            Files.deleteIfExists(Paths.get(fp_sigSub));
            Files.deleteIfExists(Paths.get(fp_sigExt));

            Files.deleteIfExists(Paths.get(fp_scopeTop));
            Files.deleteIfExists(Paths.get(fp_scopeSub));
            Files.deleteIfExists(Paths.get(fp_scopeExt));
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
                        ParseTree tree = parser.specification();

                        System.out.println(filePath.toFile());

                        //Signatures

                        //all top-level sigs in the model
                        List<String> topSigs = SigRetriever.getSigs("top", parser, tree);
                        //all subset signatures in a model
                        List<String> subsets = SigRetriever.getSigs("subsets", parser, tree);
                        //all signature extensions in a model
                        List<String> extensions_sig = SigRetriever.getSigs("extensions", parser, tree);

                        //Enums
                        //top-level enums
                        List<String> topEnums = EnumRetriever.getEnums("top", parser, tree);
                        List<String> extEnums = EnumRetriever.getEnums("extensions", parser, tree);

                        //Combining Sigs + Enums
                        List<String> top = Stream.concat(topSigs.stream(), topEnums.stream())
                                .collect(Collectors.toList());
                        List<String> extensions = Stream.concat(extensions_sig.stream(), extEnums.stream())
                                .collect(Collectors.toList());

                        //Extracting all sig names from run/check commands (typescope part of it)
                        ParseTreePattern p_command = parser.compileParseTreePattern
                                ("<name>", ALLOYParser.RULE_name);
                        List<ParseTreeMatch> matches_command = p_command.findAll(tree, "//typescope/*");

                        List<String> names = matches_command.stream().map(m -> m.get("name").getText())
                                .collect(Collectors.toList());

                        //Classifying sig names at the query (command) level
                        List<String> topScope_cmd = names.stream().filter(top::contains).collect(Collectors.toList());
                        List<String> subScope_cmd = names.stream().filter(subsets::contains).collect(Collectors.toList());
                        List<String> extScope_cmd = names.stream().filter(extensions::contains).collect(Collectors.toList());


                        System.out.println("Top scopes at the command level: " + topScope_cmd.size() + " " + topScope_cmd);
                        System.out.println("Subset scopes at the command level: "+ subScope_cmd.size() + " " + subScope_cmd);
                        System.out.println("Extension scopes at the command level: "+ extScope_cmd.size() + " " + extScope_cmd);
                        System.out.println();

                        //writing top and sub scope counts at query (command) level
                        writeResults(fp_cmdScopeTop, topScope_cmd.size());
                        writeResults(fp_cmdScopeSub, subScope_cmd.size());
                        writeResults(fp_cmdScopeExt, extScope_cmd.size());

                        //Incrementing total number of top and sub scopes at the query level
                        totTop_cmd += topScope_cmd.size();
                        totSub_cmd += subScope_cmd.size();
                        totExt_cmd += extScope_cmd.size();

                        //all signature name with a declared multiplicity "one"
                        List<String> oneSigs = SigRetriever.getSigs("one", parser, tree);

                        //all signature name with a declared multiplicity "lone"
                        List<String> loneSigs = SigRetriever.getSigs("lone", parser, tree);

                        //all signature name with a declared multiplicity "some"
                        List<String> someSigs = SigRetriever.getSigs("some", parser, tree);

                        //all signatures with a declared multiplicity
                        List<String> allMultSigs = SigRetriever.getSigs("allmult", parser, tree);

                        LinkedHashMap<String, List<String>> multSigs = new LinkedHashMap<>();
                        multSigs.put("One", oneSigs);
                        multSigs.put("Lone", loneSigs);
                        multSigs.put("Some", someSigs);

                        LinkedHashMap<String, List<String>> multFiles = new LinkedHashMap<>();
                        multFiles.put("One", Arrays.asList(fp_oneTop, fp_oneSub, fp_oneExt));
                        multFiles.put("Lone", Arrays.asList(fp_loneTop, fp_loneSub, fp_loneExt));
                        multFiles.put("Some", Arrays.asList(fp_someTop, fp_someSub, fp_someExt));


                        int totSubScope = 0;
                        int totTopScope = 0;
                        int totExtScope = 0;
                        //Classifying sig names at the signature declaration level
                        for (Map.Entry<String, List<String>> entry : multSigs.entrySet()) {
                            List<String> topScope_sig = entry.getValue().stream().filter(top::contains).
                                    collect(Collectors.toList());
                            List<String> subScope_sig = entry.getValue().stream().filter(subsets::contains).
                                    collect(Collectors.toList());
                            List<String> extScope_sig = entry.getValue().stream().filter(extensions::contains).
                                    collect(Collectors.toList());

                            System.out.println(entry.getKey() + " top scopes at the sig level: "
                                    + topScope_sig.size() + " " + topScope_sig);
                            System.out.println(entry.getKey() + " subset scopes at the sig level: "
                                    + subScope_sig.size() + " " + subScope_sig);
                            System.out.println(entry.getKey() + " extensions scopes at the sig level: "
                                    + extScope_sig.size() + " " + extScope_sig);

                            System.out.println();

                            totSubScope += subScope_sig.size();
                            totTopScope += topScope_sig.size();
                            totExtScope += extScope_sig.size();

                            //writing top and sub scope counts at sig level
                            List<String> files = multFiles.get(entry.getKey());
                            writeResults(files.get(0), topScope_sig.size());
                            writeResults(files.get(1), subScope_sig.size());
                            writeResults(files.get(2), extScope_sig.size());
                        }

                        //Incrementing total number of top and sub scopes at the sig level
                        totTop_sig += totTopScope;
                        totSub_sig += totSubScope;
                        totExt_sig += totExtScope;

                        List<String> topMultSig = allMultSigs.stream().filter(top::contains)
                                .collect(Collectors.toList());
                        List<String> subMultSig = allMultSigs.stream().filter(subsets::contains)
                                .collect(Collectors.toList());
                        List<String> extMultSig = allMultSigs.stream().filter(extensions::contains)
                                .collect(Collectors.toList());

                        //writing overall top and sub scope counts
                        writeResults(fp_scopeTop, totTopScope + topScope_cmd.size());
                        writeResults(fp_scopeSub, totSubScope + subScope_cmd.size());
                        writeResults(fp_scopeExt, totExtScope + extScope_cmd.size());
                        writeResults(fp_sigTop, topMultSig.size());
                        writeResults(fp_sigSub, subMultSig.size());
                        writeResults(fp_sigExt, extMultSig.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total top scopes at command level: " + totTop_cmd);
            System.out.println("Total sub scopes at command level: " + totSub_cmd);
            System.out.println("Total ext scopes at command level: " + totExt_cmd);
            System.out.println("Total scopes set at command level: " + (totTop_cmd + totSub_cmd + totExt_cmd));

            System.out.println("Total top scopes at sig level: " + totTop_sig);
            System.out.println("Total sub scopes at sig level: " + totSub_sig);
            System.out.println("Total ext scopes at sig level: " + totExt_sig);
            System.out.println("Total scopes set at sig level: " + (totTop_sig + totSub_sig + totExt_sig));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
