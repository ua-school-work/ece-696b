package edu.arizona.josesosa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Provides the source data for the pipeline.
 */
public class Input {
    private final Path filePath;

    public Input(String fileName) {
        this.filePath = Path.of(fileName);
    }

    /**
     * Reads the file and returns its content as a lazy Stream of strings.
     * The stream should be managed with a try-with-resources block by the caller.
     */
    public Stream<String> lines() throws IOException {
        return Files.lines(filePath);
    }
}
