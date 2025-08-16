package edu.arizona.josesosa.enterprise.view.render;

import edu.arizona.josesosa.enterprise.view.bind.TemplateRenderer;

import java.io.IOException;

/**
 * Single-responsibility class for rendering HTML views.
 */
public class HtmlViewService implements ViewService {
    private final TemplateRenderer renderer;

    public HtmlViewService(TemplateRenderer renderer) {
        this.renderer = renderer;
    }

    public String render() throws IOException {
        return renderer.renderResourceWithMethodBindings("/templates/index.html");
    }
}
