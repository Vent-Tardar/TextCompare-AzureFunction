package text.extractors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextExtractor {
    /**
     * Extract text from file to list of strings
     *
     * @param stream   InputStream containing a text file
     * @return Array list of strings containing lines of text from file
     * @throws IOException If an input or output exception occurred
     */
    public static ArrayList<String> extractText(InputStream stream) throws IOException {
        ArrayList<String> text = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            while ((line = reader.readLine()) != null)
                text.add(line);
        }
        catch (IOException ex) {
            throw ex;
        }
        return text;
    }
}