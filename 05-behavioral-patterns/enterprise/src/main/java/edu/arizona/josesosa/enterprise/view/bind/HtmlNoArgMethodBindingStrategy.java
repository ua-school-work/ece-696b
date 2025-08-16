package edu.arizona.josesosa.enterprise.view.bind;

import java.lang.reflect.Method;

/**
 * Binds a token to a controller no-arg method with the same name (e.g., {{ name }}).
 */
public class HtmlNoArgMethodBindingStrategy implements HtmlMethodBindingStrategy {
    @Override
    public String bindToken(Object controller, String name) throws Exception {
        try {
            Method method = controller.getClass().getMethod(name);
            Object value = method.invoke(controller);
            return HtmlMethodBindingTemplateRenderer.stringify(value);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
