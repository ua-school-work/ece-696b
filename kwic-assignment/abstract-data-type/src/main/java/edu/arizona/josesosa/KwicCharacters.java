package edu.arizona.josesosa;

import java.util.List;

public class KwicCharacters extends Characters{
    public KwicCharacters() {
        super();
    }

    public KwicCharacters(List<String> lines) {
        super(lines);
    }

    @Override
    public void setChar(List<String> characters) {
        lines = characters;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }
}
