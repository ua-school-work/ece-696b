// This code is for instructional purposes only, and it in essence a fork of
// https://github.com/klimesf/kwic/tree/master

package edu.arizona.josesosa;

public class Main {
    // There is no shared memory in this architecture. Each object is responsible for its own data.
    public static void main(String[] args) throws Exception {
        // Abstract Data Type = Input :: Concrete Data Object = KwicFileInput
        Input input = new KwicFileInput("src/main/resources/input.txt");
        // Concrete Data Object is asked to perform an operation via its interface and main waits for the result
        var readInput = input.readAll();

        // Abstract Data Type = Characters :: Concrete Data Object = KwicCharacters
        // The KwicCharacters object is created with the read input from the Input object
        Characters characters = new KwicCharacters(readInput);
        // Concrete Data Object is asked to perform an operation via its interface and main waits for the result
        var lines = characters.getLines();

        // So on...
        CircularShift shifter = new KwicCircularShift(lines);
        var shiftedLines = shifter.getShiftedLines();

        Alphabetizer alphabetizer = new KwicAlphabetizer(shiftedLines);
        var sortedLines = alphabetizer.getLines();

        Output output = new KwicFileOutput("src/main/resources/out.txt");
        output.writeLines(sortedLines);
        output.close();
    }
}