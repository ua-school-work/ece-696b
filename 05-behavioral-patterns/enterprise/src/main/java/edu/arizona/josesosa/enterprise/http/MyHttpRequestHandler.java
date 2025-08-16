package edu.arizona.josesosa.enterprise.http;

import java.io.IOException;
import java.util.Map;

/**
 * Single-responsibility class for HTTP-specific concerns: request method checks,
 * parsing of query/form, and sending responses (HTML/redirect).
 */
public class MyHttpRequestHandler implements HttpRequestHandler {
    private final RichHttpExchange exchange;

    public MyHttpRequestHandler(RichHttpExchange exchange) {
        this.exchange = exchange;
    }

    public String method() {
        return exchange.getRequestMethod();
    }

    public boolean ensureMethodOrRedirect(String expectedMethod, String redirectTo) throws IOException {
        if (!expectedMethod.equalsIgnoreCase(method())) {
            sendRedirect(redirectTo);
            return true;
        }
        return false;
    }

    public Map<String, String> parseForm() throws IOException {
        return HttpSupport.parseForm(exchange.getRequestBody());
    }

    public Map<String, String> parseQuery() {
        return UrlQueryStringParser.parse(exchange.getRequestURI().getRawQuery());
    }

    public void sendHtml(int status, String html) throws IOException {
        exchange.sendHtml(status, html);
    }

    public void sendRedirect(String location) throws IOException {
        exchange.sendRedirect(location);
    }
}
