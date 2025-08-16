package edu.arizona.josesosa.enterprise.view.render;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Element implements Node {
    private final String tagName;
    private final Map<String, String> attrs = new LinkedHashMap<>();
    private final List<Node> children = new ArrayList<>();

    public Element(String tagName) {
        this.tagName = java.util.Objects.requireNonNull(tagName);
    }

    public Element attr(String name, String value) {
        if (value != null) attrs.put(name, value);
        return this;
    }

    public Element add(Node child) {
        if (child != null) children.add(child);
        return this;
    }

    public Element addAll(Node... nodes) {
        if (nodes != null) {
            for (Node n : nodes) add(n);
        }
        return this;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append('<').append(tagName);
        for (Map.Entry<String, String> e : attrs.entrySet()) {
            sb.append(' ').append(e.getKey()).append("=\"").append(e.getValue()).append('\"');
        }
        sb.append('>');
        for (Node c : children) sb.append(c.render());
        sb.append("</").append(tagName).append('>');
        return sb.toString();
    }
}
