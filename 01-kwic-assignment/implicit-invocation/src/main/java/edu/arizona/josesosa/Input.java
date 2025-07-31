package edu.arizona.josesosa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Input {
    void readFile(Lines lines, File file) throws IOException {
        for (String line : Files.readAllLines(file.toPath())) {
            lines.insert(line);
        }
    }
}
