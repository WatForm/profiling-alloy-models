package com.alloyprofiling.patternsofuse.scopes;

import com.alloyprofiling.ALLOYLexer;
import com.alloyprofiling.ALLOYParser;
import com.alloyprofiling.retrievers.SigRetriever;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alloyprofiling.ResultWriter.writeResults;

public class OrderingNonExact {
    private static int totOrd = 0;
    private static int totOrdNonExact = 0;
    private static int totOrd_defaultNonExact = 0;
    private static int totOrd_setNonExact = 0;
    private static int totOrd_derivedNonExact = 0;
    private static  List<String> getSetScopes(ParseTreeMatch match, String type,  ALLOYParser parser) {

        List<ParseTree> typescopes = match.getAll("typescopes");

        if (typescopes.isEmpty())
            typescopes =  match.getAll("but_typescopes");

        List<ParseTree> setScopes = new ArrayList<>();

        if (type.equals("all"))
            typescopes.forEach(ts -> setScopes.addAll((XPath.findAll(ts, "//typescope", parser))));

        else {
            switch (type) {
                case "exact":
                    typescopes.forEach(ts -> setScopes.addAll((XPath.findAll(ts, "//typescope", parser)).
                            stream().filter(t -> t.getText().contains("exactly")).
                            collect(Collectors.toList())));
                    break;

                case "non-exact":
                    typescopes.forEach(ts -> setScopes.addAll((XPath.findAll(ts, "//typescope", parser)).
                            stream().filter(t -> !(t.getText().contains("exactly"))).
                            collect(Collectors.toList())));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }

        return getSigs(setScopes, parser);
    }
    private static List<String> getSigs(List<ParseTree> treeList, ALLOYParser parser) {
        List<String> sigs =  new ArrayList<>();
        for (ParseTree t: treeList) {
            List<String> names = XPath.findAll(t, "//name", parser)
                    .stream().map(ParseTree::getText).collect(Collectors.toList());
            sigs.addAll(names);
        }
        return sigs;
    }

    public static void main(String[] args) {
        //results directory path
        String directoryName = "Results\\PatternsOfUse\\Scopes\\";

        //creating directory if it doesn't exist
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdirs();
        }

        String path = "corpus";
        System.out.println("Counting ordering sets with non-exact scopes in Alloy models in " + path);

        //files containing the number of ordered sets with a non-exact scope
        String fp_ordNonExact = directoryName + "ordNonExact.txt";
        String fp_defaultNonExact = directoryName + "ordNonExact_def.txt";
        String fp_setNonExact = directoryName + "ordNonExact_set.txt";
        String fp_derivedNonExact = directoryName + "ordNonExact_der.txt";

        //deleting results file if it already exists
        try {
            Files.deleteIfExists(Paths.get(fp_ordNonExact));
            Files.deleteIfExists(Paths.get(fp_defaultNonExact));
            Files.deleteIfExists(Paths.get(fp_setNonExact));
            Files.deleteIfExists(Paths.get(fp_derivedNonExact));

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

                        //pattern to find open commands
                        ParseTreePattern p = parser.compileParseTreePattern("<priv> open <name> <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches = p.findAll(tree, "//specification/*");

                        for (ParseTreeMatch m: matches) {
                            String module = m.get("name").getText();
                            System.out.println(module);
                            List<String> params;
                            if (module.contains("ordering")){
                                params = XPath.findAll(m.get("para_open"), "//name", parser).stream()
                                        .map(ParseTree::getText).collect(Collectors.toList());
                                totOrd += params.size();
                            }
                        }


                        //getting all signature names
                        List<String> signatures = SigRetriever.getSigs("all", parser, tree);

                        //DEFAULT NON-EXACT

                        //list containing names of all Default Non-Exact signatures
                        List<String> defaultNonExact = new ArrayList<>();

                        //Pattern to find all run/check command without "for"
                        ParseTreePattern p_defaultNonExact = parser.compileParseTreePattern
                                ("<name_cmd_opt> <run_or_check> <nameOrBlock>",
                                        ALLOYParser.RULE_cmdDecl);
                        List<ParseTreeMatch> matches_defaultNonExact = p_defaultNonExact.findAll(tree, "//paragraph/*");

                        matches_defaultNonExact.forEach(m -> defaultNonExact.addAll(signatures));

                        // SET EXACT AND SET NON-EXACT

                        List<ParseTree> setNonExact = new ArrayList<>();

                        //patterns to command declarations with set scopes
                        ParseTreePattern p_set = parser.compileParseTreePattern
                                ("<name_cmd_opt> <run_or_check> <nameOrBlock> for <typescopes> <expect_digit>",
                                        ALLOYParser.RULE_cmdDecl);

                        ParseTreePattern p_set_num = parser.compileParseTreePattern
                                ("<name_cmd_opt> <run_or_check> <nameOrBlock> for <number> <but_typescopes> <expect_digit>",
                                        ALLOYParser.RULE_cmdDecl);

                        List<ParseTreeMatch> matches_set1 = p_set.findAll(tree, "//paragraph/*");
                        List<ParseTreeMatch> matches_set2 = p_set_num.findAll(tree, "//paragraph/*");


                        //extracting typescopes without the keyword "exactly"
                        matches_set1.forEach(m -> m.getAll("typescopes").
                                forEach(ts -> setNonExact.addAll((XPath.findAll(ts, "//typescope", parser)).
                                        stream().filter(t -> !(t.getText().contains("exactly"))).
                                        collect(Collectors.toList()))));


                        //extracting but_typescopes without the keyword "exactly"
                        matches_set2.forEach(m -> m.getAll("but_typescopes").
                                forEach(ts -> setNonExact.addAll((XPath.findAll(ts, "//typescope", parser)).
                                        stream().filter(t -> !(t.getText().contains("exactly"))).
                                        collect(Collectors.toList()))));

                        //finding signatures names with non-exact scopes
                        List<String> sigs_setNonExact = getSigs(setNonExact, parser);

                        // DERIVED NON-EXACT

                        List<String> derivedNonExact = new ArrayList<>();

                        //getting signatures with set multiplicities
                        List<String> sets_mult = SigRetriever.getSigs("allmult", parser, tree);

                        List<ParseTreeMatch> set_matches = Stream.concat(
                                matches_set1.stream(), matches_set2.stream()).collect(Collectors.toList());

                        //pattern to find children sig declarations
                        ParseTreePattern p_children = parser.compileParseTreePattern("<priv> <abs_multiplicity> sig <names> extends <name> { <decls> } <block_opt>", ALLOYParser.RULE_sigDecl);
                        List<ParseTreeMatch> matches_children = p_children.findAll(tree, "//paragraph/*");

                        //extracting names of parent (top-level) signatures
                        List<String> topLevelSigs = matches_children.stream().map(m -> m.get("name").getText()).
                                collect(Collectors.toList());

                        //LinkedHashMap containing topLevelSigs and their kids
                        LinkedHashMap<String, List<String>> top_extensions= new LinkedHashMap<>();

                        for (ParseTreeMatch m: matches_children) {
                            String parent_sig = m.get("name").getText();
                            if (top_extensions.containsKey(parent_sig)) {
                                List<String> value = new ArrayList<>(top_extensions.get(parent_sig));
                                List<String> added = Arrays.asList(m.get("names").getText().split(","));
                                value.addAll(added);
                                top_extensions.put(parent_sig, value);
                            }
                            else {
                                top_extensions.put(parent_sig,Arrays.asList(m.get("names").getText().split(",")));
                            }
                        }

                        for (ParseTreeMatch m: set_matches) {
                            List<String> setNonExact_m = getSetScopes(m, "non-exact", parser);
                            List<String> names = XPath.findAll(m.getTree(), "//name", parser).
                                    stream().map(ParseTree::getText).
                                    filter(signatures::contains).collect(Collectors.toList());
                            List<String> nonExact_topLevel = names.stream().filter(s -> topLevelSigs.contains(s) &&
                                    setNonExact_m.contains(s)).collect(Collectors.toList());
                            for (String topSig: nonExact_topLevel) {
                                List<String> extensions =  top_extensions.get(topSig);
                                List<String> ext_no_scope = extensions.stream().filter(e ->
                                        !names.contains(e) && !sets_mult.contains(e)).collect(Collectors.toList());
                                derivedNonExact.addAll(ext_no_scope);
                            }

                            List<String> top_no_scope = topLevelSigs.stream().filter(s -> !names.contains(s)).
                                    distinct().collect(Collectors.toList());

                            for (String topSig: top_no_scope) {
                                List<String> extensions_top =  top_extensions.get(topSig);
                                List<String> scoped_extensions = extensions_top.stream().
                                        filter(s -> names.stream().anyMatch(extensions_top::contains) &&
                                                (sets_mult.contains(s) || setNonExact_m.contains(s))).
                                        collect(Collectors.toList());

                                if (extensions_top.size() == scoped_extensions.size())
                                    derivedNonExact.add(topSig);
                            }

                        }

                        //Sets that have ordering module applied to them
                        ParseTreePattern p_ordSet = parser.compileParseTreePattern("<priv> open util/ordering <para_open> <as_name_opt>", ALLOYParser.RULE_open);
                        List<ParseTreeMatch> matches_ordSet = p_ordSet.findAll(tree, "//specification/*");

                        List<ParseTree> ordSet_trees = matches_ordSet.stream().map(ParseTreeMatch::getTree)
                                .collect(Collectors.toList());

                        List<String> sets = new ArrayList<>();

                        for (ParseTree t: ordSet_trees) {
                            Collection<ParseTree> sets_expr =  XPath.findAll(t, "//para_open/names_opt/names/name", parser);
                            sets.addAll(sets_expr.stream().map(ParseTree::getText).collect(Collectors.toList()));
                        }

                        List<String> ord_defaultNonExact = sets.stream().filter(s -> defaultNonExact.contains(s))
                                .collect(Collectors.toList());
                        List<String> ord_setNonExact = sets.stream().filter(s -> sigs_setNonExact.contains(s))
                                .collect(Collectors.toList());
                        List<String> ord_derivedNonExact = sets.stream().filter(s -> derivedNonExact.contains(s))
                                .collect(Collectors.toList());
                        List<String> ordNonExact = sets.stream().filter(s ->
                                        (sigs_setNonExact.contains(s) || defaultNonExact.contains(s)
                                                || derivedNonExact.contains(s))).collect(Collectors.toList());

                        //Printing out results
                        System.out.println(filePath.toFile());
                        System.out.println("Ordering with Default Non-Exact: " + ord_defaultNonExact);
                        System.out.println("Ordering with Set Non-Exact: " + ord_setNonExact);
                        System.out.println("Ordering with Derived Non-Exact: " + ord_derivedNonExact);
                        System.out.println();
                        System.out.println("Ordering Non-Exact: " + ordNonExact.size() + " " + ordNonExact);

                        //writing result files containing the number of ordered sets with non-exact scopes
                        writeResults(fp_ordNonExact, ordNonExact.size());
                        writeResults(fp_defaultNonExact, ord_defaultNonExact.size());
                        writeResults(fp_setNonExact, ord_setNonExact.size());
                        writeResults(fp_derivedNonExact, ord_derivedNonExact.size());

                        //Incrementing totals
                        totOrdNonExact += ordNonExact.size();
                        totOrd_defaultNonExact += ord_defaultNonExact.size();
                        totOrd_setNonExact += ord_setNonExact.size();
                        totOrd_derivedNonExact += ord_derivedNonExact.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Total Ordering Non-Exact: " + totOrdNonExact);
            System.out.println("Total Ordering Default Non-Exact: " + totOrd_defaultNonExact);
            System.out.println("Total Ordering Set Non-Exact: " + totOrd_setNonExact);
            System.out.println("Total Ordering Derived Non-Exact: " + totOrd_derivedNonExact);
            System.out.println("Total Ordering: " + totOrd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
