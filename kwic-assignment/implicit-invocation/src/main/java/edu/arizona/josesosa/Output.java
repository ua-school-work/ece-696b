package edu.arizona.josesosa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Output {
    void writeFile(Lines lines, File file) throws IOException {
        // Use a try-with-resources statement to ensure the FileWriter is always closed.
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (String line : lines.all()) {
                fileWriter.append(line).append(System.lineSeparator());
            }
        }
    }
}
