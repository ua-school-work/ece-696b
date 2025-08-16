package edu.arizona.josesosa.enterprise.controller;

import edu.arizona.josesosa.enterprise.http.HttpRequestHandler;
import edu.arizona.josesosa.enterprise.view.render.ViewService;

import java.io.IOException;
import java.util.Map;

/**
 * BaseController implements the Template Method pattern for common web-controller flows.
 * Concrete controllers implement the small set of hooks while this class handles
 * HTTP method checks, parsing, redirects, and view rendering.
 */
public abstract class BaseController {
    protected ViewService views;
    protected HttpRequestHandler http;

    protected BaseController() {
    }

    protected BaseController(ViewService views, HttpRequestHandler http) {
        this.views = views;
        this.http = http;
    }

    /**
     * Inject the view service used to render the current view.
     */
    public void setViews(ViewService views) {
        this.views = views;
    }

    /**
     * Inject the HTTP helper used for request/response utilities.
     */
    public void setHttp(HttpRequestHandler http) {
        this.http = http;
    }

    /**
     * Handle GET / by rendering the current view after running a pre-hook.
     */
    public final void handleRoot() throws IOException {
        if (http.ensureMethodOrRedirect("GET", "/")) return;
        beforeRoot();
        String html = views.render();
        http.sendHtml(200, html);
    }

    /**
     * Handle POST /submit by parsing form data, delegating to onSubmit, and redirecting.
     */
    public final void handleSubmit() throws IOException {
        if (http.ensureMethodOrRedirect("POST", "/")) return;
        Map<String, String> form = http.parseForm();
        onSubmit(form);
        http.sendRedirect("/");
    }

    /**
     * Handle GET /edit by parsing query params, delegating to onEdit, and rendering the view.
     */
    public final void handleEdit() throws IOException {
        if (http.ensureMethodOrRedirect("GET", "/")) return;
        Map<String, String> q = http.parseQuery();
        onEdit(q);
        String html = views.render();
        http.sendHtml(200, html);
    }

    /**
     * Handle /delete by parsing query params, delegating to onDelete, then redirecting.
     */
    public final void handleDelete() throws IOException {
        Map<String, String> q = http.parseQuery();
        onDelete(q);
        http.sendRedirect("/");
    }

    // Hooks for concrete controllers
    /** Pre-render hook for the root view; subclasses may prepare state. */
    protected void beforeRoot() throws IOException {
    }

    /** Called after POST form parsing to create or update entities. */
    protected abstract void onSubmit(Map<String, String> form) throws IOException;

    /** Called on GET /edit after query parsing to prepare edit state. */
    protected abstract void onEdit(Map<String, String> query) throws IOException;

    /** Called on /delete after query parsing to remove an entity. */
    protected abstract void onDelete(Map<String, String> query) throws IOException;
}
