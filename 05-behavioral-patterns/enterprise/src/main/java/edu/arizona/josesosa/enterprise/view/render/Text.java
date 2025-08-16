package edu.arizona.josesosa.enterprise.view.render;

public final class Text implements Node {
    private final String text;

    public Text(String text) {
        this.text = text == null ? "" : text;
    }

    @Override
    public String render() {
        return text;
    }
}
