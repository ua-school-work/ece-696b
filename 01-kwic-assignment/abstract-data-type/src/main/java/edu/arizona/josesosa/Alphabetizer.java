package edu.arizona.josesosa;

import java.util.Collections;
import java.util.List;

public abstract class Alphabetizer {
    protected List<String> lines;

    public Alphabetizer() {
    }

    public Alphabetizer(List<String> lines) {
        this.lines = alphabetize(lines);
    }


    private List<String> alphabetize(List<String> lines){

        lines.sort(String.CASE_INSENSITIVE_ORDER);
        return lines;
    }

    protected List<String> getLines() {
        // This method should be overridden by subclasses to return the alphabetized lines.
        return null;
    }
}
