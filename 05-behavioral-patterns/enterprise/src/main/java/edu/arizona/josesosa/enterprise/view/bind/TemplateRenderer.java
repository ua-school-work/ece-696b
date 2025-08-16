package edu.arizona.josesosa.enterprise.view.bind;

import java.io.IOException;

/**
 * Port interface for a template renderer that binds controller methods/fields.
 */
public interface TemplateRenderer {
    String renderResourceWithMethodBindings(String resourcePath) throws IOException;
}
