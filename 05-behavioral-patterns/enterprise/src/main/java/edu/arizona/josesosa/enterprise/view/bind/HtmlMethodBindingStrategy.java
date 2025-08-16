package edu.arizona.josesosa.enterprise.view.bind;

/**
 * Strategy (GoF) for binding an HTML template token to a controller value via methods/fields.
 * Implementations return the stringified value if they can bind the token, or null if not applicable.
 */
public interface HtmlMethodBindingStrategy {
    String bindToken(Object controller, String name) throws Exception;
}
