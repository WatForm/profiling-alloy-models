package com.alloyprofiling.unused;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinesOfCode {
    public static void main(String[] args) {
        try {
            String s = readFile("C:\\Users\\eeid\\Desktop\\subModels\\MultipleInheritance.als");

            Pattern p = Pattern.compile("/\\*[\\s\\S]*?\\*/|(//)[^\\n\\r]*[\\n\\r]|(--)[^\\n\\r]*[\\n\\r]");

            Matcher m = p.matcher(s);

            int total = 0;
            while (m.find()) {

                String lines[] = m.group(0).split("\n");
                for (String string : lines) {
                    System.out.println(string);
                    if (!string.isBlank())
                        total++;
                }
            }
            System.out.println("Total line for comments = " + total);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
}
