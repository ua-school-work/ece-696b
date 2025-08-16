package edu.arizona.josesosa.enterprise.http;

import java.util.Map;

/**
 * Utility responsible for parsing URL query strings (the part after '?').
 * This class has a single responsibility: convert a raw query string into a Map of key/value pairs.
 */
public final class UrlQueryStringParser {
    private UrlQueryStringParser() {
    }

    /**
     * Parse a URL raw query string into a map of decoded key/value pairs.
     * Never queries any database; purely string parsing/decoding.
     */
    public static Map<String, String> parse(String rawQuery) {
        return new UrlQueryInterpreter().parse(rawQuery);
    }
}
