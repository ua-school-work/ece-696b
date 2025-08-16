package edu.arizona.josesosa.enterprise.view.bind;

import java.lang.reflect.Method;

/**
 * Binds an explicit token call like {{ name() }} to the corresponding no-arg method on the controller.
 */
public class HtmlExplicitMethodCallBindingStrategy implements HtmlMethodBindingStrategy {
    @Override
    public String bindToken(Object controller, String name) throws Exception {
        Method method = controller.getClass().getMethod(name);
        Object value = method.invoke(controller);
        return HtmlMethodBindingTemplateRenderer.stringify(value);
    }
}
