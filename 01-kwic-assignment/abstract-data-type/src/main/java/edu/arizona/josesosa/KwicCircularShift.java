package edu.arizona.josesosa;

import java.util.List;

public class KwicCircularShift extends CircularShift{
    public KwicCircularShift(List<String> lines) {
        super(lines);
    }

    public KwicCircularShift() {
        super();
    }

    @Override
    public List<String> getShiftedLines() {
        return lines;
    }
}
