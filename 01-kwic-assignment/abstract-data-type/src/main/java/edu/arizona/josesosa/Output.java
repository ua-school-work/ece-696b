package edu.arizona.josesosa;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public abstract class Output {
    protected PrintWriter out;

    public Output(String filename) throws FileNotFoundException {
        out = new PrintWriter(filename);
    }

    protected void writeLines(List<String> lines) {
        // This method should be overridden by subclasses to write the lines to the output.
    }

    public void close() {
        // This method should be overridden by subclasses to close the output stream.
    }
}
