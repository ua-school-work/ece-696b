package edu.arizona.josesosa.enterprise.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP transport utilities: sending responses, redirects, and basic form/url decoding.
 * Parsing of URL query strings is handled by UrlQueryStringParser.
 */
public final class HttpSupport {
    private HttpSupport() {
    }


    public static Map<String, String> parseForm(InputStream is) throws IOException {
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return parseUrlEncoded(body);
    }

    public static Map<String, String> parseUrlEncoded(String s) {
        return new UrlQueryInterpreter().parse(s);
    }

    public static String urlDecode(String s) {
        // Standard application/x-www-form-urlencoded decoding:
        // '+' represents a space, and percent-encoded sequences are decoded using UTF-8.
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}
