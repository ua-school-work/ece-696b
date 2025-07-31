// This code is for instructional purposes only, and it in essence a fork of
// https://github.com/klimesf/kwic/tree/master
package edu.arizona.josesosa;

import java.io.*;
import java.util.Arrays;

public class Main {
    // *** This section represents shared storage for the architecture. ***

    /**
     * Keeps all the characters.
     */
    private static char[] chars = new char[10];
    private static int charCounter = 0;

    /**
     * Keeps indexes of each line starting character.
     */
    private static int[] lineIndexes = new int[10];

    /**
     * Keeps a 2D array of indexes of circular shifts of the lines.
     * First key is start of the line from lineIndexes. The second index marks where the line starts.
     */
    private static int[][] wordIndexes;
    private static int wordCounter = 0;

    /**
     * Keeps a 2D array of indexes of alphabetically sorted circular shifts of the lines.
     * First key is start of the line from lineIndexes. The second index marks where the line starts.
     */
    private static int[][] alphabetizedIndexes;

    // *** End of Shared Storage ***

    public static void main(String[] args) throws IOException {
        // Each method call represents a subprogram in the architecture.
        input(new FileReader("src/main/resources/input.txt"));
        circularShift();
        alphabetizer();
        output(new FileWriter("src/main/resources/output.txt"));
    }

    /**
     * Read input characters from a file and saves it to shared storage.
     * Input Component.
     * <p>
     * Reads an input file and saves its characters to a shared "chars" array. It also marks starts of each line by
     * saving indexes of "chars" on which the line starts to "lineIndexes" array.
     * </p>
     *
     * @throws IOException
     */
    public static void input(Reader reader) throws IOException {
        // Each subprogram has access to shared storage.
        Arrays.fill(chars, (char)-1);
        Arrays.fill(lineIndexes, -1);
        int inputChar;
        int lineIterator = 0;
        boolean newLine = true;

        while ((inputChar = reader.read()) != -1) {
            // Mark the start of the line
            if (newLine) {
                lineIndexes = expandIfNeeded(lineIndexes, lineIterator + 1);
                lineIndexes[lineIterator++] = charCounter;
                newLine = false;
            }

            // If this is the end of a line, mark it and rewrite the character to space
            // (this avoids problems while outputting the lines)
            if (inputChar == '\n' || inputChar == '\r') {
                newLine = true;// Convert to space for easier processing
                inputChar = ' ';
            }

            // Save the character
            chars = expandIfNeeded(chars, charCounter + 1);
            chars[charCounter++] = (char) inputChar;
        }
        chars[charCounter++] = ' ';
    }

    /**
     * Circular shifts the lines.
     * Circular Shift Component.
     * <p/>
     * Iterates through lines and searches for words. Each start of the word is then indexed to wordIndexes.
     * Each circular shift begins with a different word on the line; therefore, each word index is also an index
     * of a circular shift.
     * <p/>
     */
    public static void circularShift() {
        // Each subprogram has access to shared storage.
        wordIndexes = new int[lineIndexes.length][30];

        // Iterate through lines
        int lineIterator = 0;
        while (lineIterator + 1 < lineIndexes.length && lineIndexes[lineIterator] != -1) {
            int lineStart = lineIndexes[lineIterator];
            int lineEnd = lineIndexes[lineIterator + 1] != -1 ? lineIndexes[lineIterator + 1] : charCounter;

            Arrays.fill(wordIndexes[lineIterator], -1);

            // Iterate through the line chars and look for new words
            int indexIterator = 0;
            boolean newWord = true;
            for (int charIterator = lineStart; charIterator < lineEnd; ++charIterator) {
                // If this is the end of the word, skip it
                if (chars[charIterator] == ' ') {
                    newWord = true;
                    continue;
                }

                // Mark start of new word
                if (newWord) {
                    wordIndexes[lineIterator] = expandIfNeeded(wordIndexes[lineIterator], indexIterator + 1);
                    wordIndexes[lineIterator][indexIterator++] = charIterator;
                    ++wordCounter;
                    newWord = false;
                }
            }

            // Bump line iterator
            ++lineIterator;
        }
    }

    /**
     * Alphabetically orders all circular shifts of each line in the file.
     * Alphabetizer Component.
     */
    public static void alphabetizer() {
        // Each subprogram has access to shared storage.
        alphabetizedIndexes = new int[wordCounter][3];
        int index = 0;
        for (int line = 0; line < lineIndexes.length && lineIndexes[line] != -1; ++line) {
            for (int word = 0; word < wordIndexes[line].length && wordIndexes[line][word] != -1; ++word, ++index) {
                alphabetizedIndexes[index][0] = line;   // Line index
                alphabetizedIndexes[index][1] = word;   // Word index in line
                alphabetizedIndexes[index][2] = wordIndexes[line][word]; // Word index in chars
            }
        }

        // Iterate through words
        for (int bubbleIndex = 0; bubbleIndex < alphabetizedIndexes.length - 1; ++bubbleIndex)
            for (index = 0; index < alphabetizedIndexes.length - bubbleIndex - 1; ++index) {
                int wordIndex = wordIndexes[alphabetizedIndexes[index][0]][alphabetizedIndexes[index][1]];
                int nextWordIndex = wordIndexes[alphabetizedIndexes[index + 1][0]][alphabetizedIndexes[index + 1][1]];

                char wordChar = (char) -1;
                char nextWordChar = (char) -1;

                // Read chars until they aren't equal
                for (; wordChar == nextWordChar && nextWordIndex < chars.length;
                     ++wordIndex, ++nextWordIndex) {
                    wordChar = chars[wordIndex];
                    nextWordChar = chars[nextWordIndex];
                    // The buggy increment lines have been removed from here.
                }

                // Now that chars aren't equal, compare them case-insensitively
                if (Character.toLowerCase(wordChar) > Character.toLowerCase(nextWordChar)) {
                    // Bubble sort switch
                    int[] temp = alphabetizedIndexes[index];
                    alphabetizedIndexes[index] = alphabetizedIndexes[index + 1];
                    alphabetizedIndexes[index + 1] = temp;
                }
            }
    }

    /**
     * Outputs alphabetically sorted circular shifts of the lines.
     * Output Component.
     */
    public static void output(Writer writer) throws IOException {
        // Each subprogram has access to shared storage.
        for (int[] alphabetizedIndex : alphabetizedIndexes) {
            int wordStart = alphabetizedIndex[2];
            int lineStart = lineIndexes[alphabetizedIndex[0]];
            int lineEnd = lineIndexes[alphabetizedIndex[0] + 1] != -1 ? lineIndexes[alphabetizedIndex[0] + 1] : charCounter;

            // From the word start to the end of the line
            for (int charIndex = wordStart; charIndex < lineEnd; ++charIndex) {
                writer.write(chars[charIndex]);
            }
            // From the beginning of the line to the word
            for (int charIndex = lineStart; charIndex < wordStart; ++charIndex) {
                writer.write(chars[charIndex]);
            }
            writer.write('\n');
        }
        writer.flush();
    }

    /**
     * Expands the given array if the given index is out of bounds.
     *
     * @param array original array
     * @param index the index
     * @return expanded array
     */
    private static int[] expandIfNeeded(int[] array, int index) {
        if (index >= array.length) {
            int[] temp = new int[array.length * 2];
            Arrays.fill(temp, -1);
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }
        return array;
    }

    /**
     * Expands the given array if the given index is out of bounds.
     *
     * @param array original array
     * @param index the index
     * @return expanded array
     */
    private static char[] expandIfNeeded(char[] array, int index) {
        if (index >= array.length) {
            char[] temp = new char[array.length * 2];
            Arrays.fill(temp, (char) -1);
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }
        return array;
    }
}