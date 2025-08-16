package edu.arizona.josesosa.enterprise.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Decorator for HttpExchange providing convenience methods like sendHtml and sendRedirect
 * while delegating all HttpExchange behavior to the wrapped instance.
 */
public class MyHttpExchange extends HttpExchange implements RichHttpExchange {
    private HttpExchange delegate;

    public MyHttpExchange() {
    }

    public MyHttpExchange(HttpExchange delegate) {
        this.delegate = delegate;
    }

    public void setDelegate(HttpExchange delegate) {
        this.delegate = delegate;
    }

    private HttpExchange requireDelegate() {
        if (delegate == null) throw new IllegalStateException("MyHttpExchange delegate is not set");
        return delegate;
    }

    // Convenience API
    public void sendHtml(int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        sendResponseHeaders(status, bytes.length);
        try (OutputStream os = getResponseBody()) {
            os.write(bytes);
        }
    }

    public void sendRedirect(String location) throws IOException {
        getResponseHeaders().add("Location", location);
        sendResponseHeaders(302, 0);
        try (OutputStream os = getResponseBody()) {
            // nothing to write on redirect
        }
    }

    // Delegated HttpExchange methods
    @Override
    public Headers getRequestHeaders() {
        return requireDelegate().getRequestHeaders();
    }

    @Override
    public Headers getResponseHeaders() {
        return requireDelegate().getResponseHeaders();
    }

    @Override
    public URI getRequestURI() {
        return requireDelegate().getRequestURI();
    }

    @Override
    public String getRequestMethod() {
        return requireDelegate().getRequestMethod();
    }

    @Override
    public HttpContext getHttpContext() {
        return requireDelegate().getHttpContext();
    }

    @Override
    public void close() {
        requireDelegate().close();
    }

    @Override
    public InputStream getRequestBody() {
        return requireDelegate().getRequestBody();
    }

    @Override
    public OutputStream getResponseBody() {
        return requireDelegate().getResponseBody();
    }

    @Override
    public void sendResponseHeaders(int rCode, long responseLength) throws IOException {
        requireDelegate().sendResponseHeaders(rCode, responseLength);
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return requireDelegate().getRemoteAddress();
    }

    @Override
    public int getResponseCode() {
        return requireDelegate().getResponseCode();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return requireDelegate().getLocalAddress();
    }

    @Override
    public String getProtocol() {
        return requireDelegate().getProtocol();
    }

    @Override
    public Object getAttribute(String name) {
        return requireDelegate().getAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        requireDelegate().setAttribute(name, value);
    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {
        requireDelegate().setStreams(i, o);
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return requireDelegate().getPrincipal();
    }
}
