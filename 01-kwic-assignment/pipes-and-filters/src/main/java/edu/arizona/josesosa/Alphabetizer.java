package edu.arizona.josesosa;

import java.util.List;
import java.util.stream.Stream;

/**
 * Encapsulates the logic for sorting.
 * This class is stateless and its method can be applied to any stream of strings.
 */
public class Alphabetizer {

    /**
     * A function that consumes a stream and returns a sorted List.
     * @param lines The input stream of shifted lines.
     * @return A new List containing the sorted lines.
     */
    public List<String> sort(Stream<String> lines) {
        return lines.sorted(String.CASE_INSENSITIVE_ORDER).toList();
    }
}

