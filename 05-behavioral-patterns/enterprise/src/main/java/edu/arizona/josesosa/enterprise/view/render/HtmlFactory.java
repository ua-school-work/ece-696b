package edu.arizona.josesosa.enterprise.view.render;

/**
 * Abstract Factory for constructing markup-related components used by the view layer.
 * This allows swapping the family of UI building blocks (e.g., HTML vs. another markup) without
 * changing consumers like renderers.
 */
public interface HtmlFactory {
    String escape(String s);

    Text text(String s);

    Element el(String tag, Node... children);

    Element tr(Node... children);

    Element td(Node... children);

    Element a(String href, Node... children);
}
