package src.MessierProgram;

import java.io.BufferedReader;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

public class IOHandler {

    private String path;

    public IOHandler(String path) throws InvalidEntryException {

        this.path = path;

        if (!new File(this.path).isFile()) {
            throw new InvalidEntryException("File: " + this.path + " not found.");
        }
    }

    /**
     * Reads the file for data entries.
     * 
     * @return An array list of the entries
     * @throws IOException
     */
    public ArrayList<String> getEntries() throws IOException {

        try (BufferedReader bReader = Files.newBufferedReader(
                Paths.get(this.path), StandardCharsets.UTF_8);) {

            ArrayList<String> entries = new ArrayList<String>();

            String entry = bReader.readLine();

            while (entry != null) {
                entries.add(entry);
                entry = bReader.readLine();
            }

            bReader.close();
            return entries;

        } catch (IOException exception) {
            throw exception;
        }
    }

    public String getPath() {
        return this.path;
    }
}
