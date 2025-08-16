package edu.arizona.josesosa.enterprise.view.bind;

import java.lang.reflect.Method;

/**
 * Binds a token to a JavaBean getter on the controller (e.g., token "name" -> getName()).
 */
public class HtmlGetterMethodBindingStrategy implements HtmlMethodBindingStrategy {
    @Override
    public String bindToken(Object controller, String name) throws Exception {
        String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        try {
            Method method = controller.getClass().getMethod(getter);
            Object value = method.invoke(controller);
            return HtmlMethodBindingTemplateRenderer.stringify(value);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
