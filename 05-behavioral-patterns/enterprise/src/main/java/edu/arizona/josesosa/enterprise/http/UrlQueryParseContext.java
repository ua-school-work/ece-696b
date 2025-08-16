package edu.arizona.josesosa.enterprise.http;

import java.util.LinkedHashMap;
import java.util.Map;

final class UrlQueryParseContext {
    final String input;
    int pos;
    final Map<String, String> result = new LinkedHashMap<>();

    UrlQueryParseContext(String input) {
        this.input = input == null ? "" : input;
    }

    boolean hasNext() {
        return pos < input.length();
    }

    char peek() {
        return input.charAt(pos);
    }

    void next() {
        pos++;
    }
}
