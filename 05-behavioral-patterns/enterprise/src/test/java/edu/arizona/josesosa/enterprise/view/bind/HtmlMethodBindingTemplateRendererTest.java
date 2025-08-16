package edu.arizona.josesosa.enterprise.view.bind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlMethodBindingTemplateRendererTest {

    static class TestController {
        public String name() { return "Ana"; }
        private String title = "Dr";
        public String getTitle() { return title; }
        public int age = 42;
        public String greet() { return "hey"; }
    }

    @Test
    void resolvesNoArgMethodToken() {
        HtmlMethodBindingTemplateRenderer r = new HtmlMethodBindingTemplateRenderer(new TestController());
        String out = r.renderWithMethodBindings("Hi {{ name }}.");
        assertEquals("Hi Ana.", out);
    }

    @Test
    void resolvesGetterToken() {
        HtmlMethodBindingTemplateRenderer r = new HtmlMethodBindingTemplateRenderer(new TestController());
        String out = r.renderWithMethodBindings("Title: {{ title }}");
        assertEquals("Title: Dr", out);
    }

    @Test
    void resolvesPublicFieldToken() {
        HtmlMethodBindingTemplateRenderer r = new HtmlMethodBindingTemplateRenderer(new TestController());
        String out = r.renderWithMethodBindings("Age: {{ age }}");
        assertEquals("Age: 42", out);
    }

    @Test
    void resolvesExplicitMethodCallOnly() {
        HtmlMethodBindingTemplateRenderer r = new HtmlMethodBindingTemplateRenderer(new TestController());
        String out = r.renderWithMethodBindings("Say: {{ greet() }}!");
        assertEquals("Say: hey!", out);
    }

    @Test
    void unknownTokenRendersEmpty() {
        HtmlMethodBindingTemplateRenderer r = new HtmlMethodBindingTemplateRenderer(new TestController());
        String out = r.renderWithMethodBindings("{{ unknown }}");
        assertEquals("", out);
    }
}
