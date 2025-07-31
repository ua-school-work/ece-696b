package edu.arizona.josesosa;

import java.util.List;

public abstract class Characters {
    protected List<String> lines;

    public Characters(List<String> lines) {
        this.lines = lines;
    }

    public Characters() {
    }

    public void setChar(List<String> characters) {
        // This method should be overridden in subclasses to set the characters
    }

    public List<String> getLines() {
        // This method should be overridden in subclasses to return the lines
        return null;
    }
}
