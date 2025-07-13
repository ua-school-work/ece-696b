package edu.arizona.josesosa;

import java.util.List;

public class KwicAlphabetizer extends Alphabetizer{
    public KwicAlphabetizer() {
        super();
    }

    public KwicAlphabetizer(List<String> lines) {
        super(lines);
    }

    @Override
    protected List<String> getLines() {
        return lines;
    }
}
