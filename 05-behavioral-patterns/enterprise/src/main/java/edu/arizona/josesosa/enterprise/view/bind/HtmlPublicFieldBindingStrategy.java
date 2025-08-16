package edu.arizona.josesosa.enterprise.view.bind;

/**
 * Binds a token to a public field on the controller with the same name.
 */
public class HtmlPublicFieldBindingStrategy implements HtmlMethodBindingStrategy {
    @Override
    public String bindToken(Object controller, String name) throws Exception {
        try {
            var field = controller.getClass().getField(name);
            Object value = field.get(controller);
            return HtmlMethodBindingTemplateRenderer.stringify(value);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
