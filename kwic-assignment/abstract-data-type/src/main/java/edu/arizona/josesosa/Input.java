package edu.arizona.josesosa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class Input {
    protected final String fName;

    public Input(String fileName) throws FileNotFoundException {
        fName =  fileName;
    }

    public List<String> readAll() throws IOException {
        // This method should be overridden in subclasses to set the characters
        return null;
    }
}
