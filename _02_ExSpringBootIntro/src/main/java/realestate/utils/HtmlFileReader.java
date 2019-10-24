package realestate.utils;

import java.io.*;
import java.util.StringJoiner;

public class HtmlFileReader {

    public String readHtmlFile(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
        StringJoiner sj = new StringJoiner("");
        String line;
        while ((line=bf.readLine()) != null) {
            sj.add(line);
        }
        return sj.toString();
    }
}
