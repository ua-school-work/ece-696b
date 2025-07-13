package edu.arizona.josesosa;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.IOException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Instantiate the components. They act as tools we can use.
        Input input = new Input("src/main/resources/input.txt");
        CircularShift shifter = new CircularShift();
        Alphabetizer alphabetizer = new Alphabetizer();
        Output output = new Output("src/main/resources/output.txt");

        // 2. Compose and execute the pipeline in a single, readable expression.
        // This nested structure clearly shows the data flow:
        // lines -> shifted lines -> sorted lines -> written to output
        // The try-with-resources block is essential to ensure the file stream is closed.
        try (Stream<String> lines = input.lines()) {
            output.write(
                    alphabetizer.sort(
                            shifter.shift(lines)
                    )
            );
        }
    }
}
