// This code is for instructional purposes only, and it in essence a fork of
// https://github.com/klimesf/kwic/tree/master

package edu.arizona.josesosa;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Lines support property change listeners, which allows for implicit invocation.
        Lines lines = new Lines();
        Lines shifts = new Lines();

        Input input = new Input();
        // CircularShift and Alphabetizer support on property change actions, allowing them to react to changes in Lines.
        CircularShift circularShift = new CircularShift(shifts);
        Alphabetizer alphabetizer = new Alphabetizer();
        Output output = new Output();

        // Add property change listeners to Lines and shifts so that they can react to changes.
        lines.addPropertyChangeListener(circularShift);
        shifts.addPropertyChangeListener(alphabetizer);

        //Here is where execution starts and the implicit invocation architecture is utilized.
        input.readFile(lines, new File("src/main/resources/input.txt"));
        output.writeFile(shifts, new File("src/main/resources/output.txt"));
    }
}