package edu.arizona.josesosa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Encapsulates the logic for writing the final result to a file.
 */
public class Output {
    private final Path filePath;

    public Output(String fileName) {
        this.filePath = Path.of(fileName);
    }

    /**
     * A method that takes the final data and performs the side-effect of writing it to disk.
     * @param lines The final, sorted list of lines to write.
     */
    public void write(List<String> lines) throws IOException {
        Files.write(filePath, lines);
    }
}

