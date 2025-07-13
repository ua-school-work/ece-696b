package edu.arizona.josesosa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class CircularShift {
    protected List<String> lines;

    public CircularShift(List<String> lines) {
        this.lines = circularShift(lines);
    }

    public CircularShift() {
        this.lines = new LinkedList<>();
    }

    protected List<String> getShiftedLines() {
        // This method should be overridden by subclasses to return the shifted lines.
        return null;
    }

    private List<String> circularShift(List<String> lines){
        List<String> result = new LinkedList<>();
        for (String line: lines) {
            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));
            int lastIndex = words.size() - 1;
            for (int i = 0; i < words.size() ; ++i) {
                words.addFirst(words.remove(lastIndex));
                result.add(arrToString(words));
            }
        }
        return result;
    }

    private String arrToString(List<String> arr){
        StringBuilder builder = new StringBuilder();
        for (String node: arr) {
            builder.append(node);
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
