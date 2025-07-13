package edu.arizona.josesosa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Encapsulates the logic for performing a circular shift.
 * This class is stateless and its method can be applied to any stream of strings.
 */
public class CircularShift {

    /**
     * A pure function that transforms a stream of lines into a stream of shifted lines.
     * @param lines The input stream of lines.
     * @return A new stream containing all circular shifts.
     */
    public Stream<String> shift(Stream<String> lines) {
        return lines.flatMap(this::generateShiftsForLine);
    }

    private Stream<String> generateShiftsForLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return Stream.empty();
        }
        List<String> words = new ArrayList<>(Arrays.asList(line.split("\\s+")));
        return IntStream.range(0, words.size())
                .mapToObj(i -> {
                    Collections.rotate(words, 1);
                    return String.join(" ", words);
                });
    }
}
