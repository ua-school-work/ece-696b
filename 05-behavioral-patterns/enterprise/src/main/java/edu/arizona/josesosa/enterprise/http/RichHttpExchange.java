package edu.arizona.josesosa.enterprise.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Port interface exposing only what higher layers need from MyHttpExchange.
 */
public interface RichHttpExchange {
    String getRequestMethod();

    InputStream getRequestBody();

    URI getRequestURI();

    void sendHtml(int status, String body) throws IOException;

    void sendRedirect(String location) throws IOException;

    void setDelegate(HttpExchange exchange);
}
