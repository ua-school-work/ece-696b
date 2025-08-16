package edu.arizona.josesosa.enterprise.view.render;

/**
 * Minimal HTML Composite utilities to model HTML as a tree using instance-based APIs.
 * Implements the Composite pattern via Node, Text, and Element, and exposes
 * an instance factory (Abstract Factory style) to build the tree without static methods/classes.
 */
public class Html implements HtmlFactory {
    public Html() {
    }

    // Escaping helper centralized here to keep SRP in renderers
    @Override
    public String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Factory helpers (instance methods)
    @Override
    public Text text(String s) {
        return new Text(s);
    }

    @Override
    public Element el(String tag, Node... children) {
        return new Element(tag).addAll(children);
    }

    @Override
    public Element tr(Node... children) {
        return el("tr", children);
    }

    @Override
    public Element td(Node... children) {
        return el("td", children);
    }

    @Override
    public Element a(String href, Node... children) {
        return new Element("a").attr("href", href).addAll(children);
    }
}
