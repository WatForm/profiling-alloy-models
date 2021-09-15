package com.alloyprofiling.unused.analysis;

import com.alloyprofiling.analysiscomplexity.depthjoinsquant.DepthJoin;
import com.alloyprofiling.analysiscomplexity.secondorderoperators.SetCardinality;
import com.alloyprofiling.analysiscomplexity.secondorderoperators.TCOperators;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class makeCSV {
    public static void main(String[] args) {
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get("Results\\sat_models_no_open.txt"))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("benchmarks\\run-sat-models-final-results.csv");

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            CSVWriter csvWriter = new CSVWriter(new FileWriter("benchmarks\\run-usable-sat-models-final-results.csv"));
            String[] header = { "File", "Solver", "TotalVars", "PrimaryVars", "Clauses", "Scope", "Satisfiability",
                    "Time", "SetCard", "TC", "AvgJD"};
            csvWriter.writeNext(header);

            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                if (result.contains(nextRecord[0])) {
                    int setcard = SetCardinality.countSetCard("benchmarks\\2021-06-03-12-31-50\\" + nextRecord[0]);
                    int transitiveclosure = TCOperators.countTC("benchmarks\\2021-06-03-12-31-50\\" + nextRecord[0]);
                    List<Integer> depths = DepthJoin.getDepthJoin("benchmarks\\2021-06-03-12-31-50\\" + nextRecord[0]);
                    List<String> record = new ArrayList<>(Arrays.asList(nextRecord));
                    record.add(Integer.toString(setcard));
                    if (transitiveclosure > 0)
                        record.add("1");
                    else
                        record.add("0");

                    double avgDepth = depths.stream()
                            .mapToDouble(d -> d)
                            .average()
                            .orElse(0.0);
                    record.add(Double.toString(avgDepth));

                    String[] arr = new String[record.size()];
                    // Convert the Arraylist to array
                    arr = record.toArray(arr);
                    csvWriter.writeNext(arr);
                    csvWriter.flush();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
