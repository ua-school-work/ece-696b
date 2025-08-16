package edu.arizona.josesosa.enterprise.http;

import java.io.IOException;
import java.util.Map;

/**
 * Port interface for the HTTP request helper/adapter.
 */
public interface HttpRequestHandler {
    String method();

    boolean ensureMethodOrRedirect(String expectedMethod, String redirectTo) throws IOException;

    Map<String, String> parseForm() throws IOException;

    Map<String, String> parseQuery();

    void sendHtml(int status, String html) throws IOException;

    void sendRedirect(String location) throws IOException;
}
