package edu.arizona.josesosa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class KwicFileInput extends Input{

    public KwicFileInput(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public List<String> readAll() throws IOException {
        return Files.readAllLines(Paths.get(fName));
    }
}
