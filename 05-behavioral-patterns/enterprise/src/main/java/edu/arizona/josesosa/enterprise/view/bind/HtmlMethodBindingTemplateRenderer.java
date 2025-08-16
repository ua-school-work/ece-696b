package edu.arizona.josesosa.enterprise.view.bind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Renders an HTML template by binding tokens like {{ name }} or {{ method() }}
 * to controller values via method/field strategies.
 */
public class HtmlMethodBindingTemplateRenderer implements TemplateRenderer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile("\\{\\{\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*(\\(\\))?\\s*}}");

    private final Object controller;
    private final List<HtmlMethodBindingStrategy> defaultStrategies;
    private final List<HtmlMethodBindingStrategy> explicitCallStrategies;

    public HtmlMethodBindingTemplateRenderer(Object controller) {
        this.controller = controller;
        // Order matters: try method, then getter, then field for non-explicit tokens
        this.defaultStrategies = Arrays.asList(
                new HtmlNoArgMethodBindingStrategy(),
                new HtmlGetterMethodBindingStrategy(),
                new HtmlPublicFieldBindingStrategy()
        );
        this.explicitCallStrategies = List.of(new HtmlExplicitMethodCallBindingStrategy());
    }

    /**
     * Render an HTML resource from the classpath by resolving template tokens against the controller.
     * @param resourcePath classpath-relative path to the HTML template (e.g., "/templates/index.html")
     * @return rendered HTML string
     * @throws IOException if the resource cannot be found or read
     */
    public String renderResourceWithMethodBindings(String resourcePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Resource not found: " + resourcePath);
            String text = readUtf8(is);
            return renderWithMethodBindings(text);
        }
    }

    /**
     * Render an HTML template string by resolving tokens {{ name }} and {{ name() }}
     * against the provided controller object using binding strategies.
     */
    public String renderWithMethodBindings(String template) {
        return replaceTokens(template);
    }

    private String replaceTokens(String template) {
        Matcher matcher = createTokenMatcher(template);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            appendResolvedReplacement(matcher, result);
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static Matcher createTokenMatcher(String template) {
        return TOKEN_PATTERN.matcher(template);
    }

    private static String getTokenName(Matcher matcher) {
        return matcher.group(1);
    }

    private static boolean isExplicitCall(Matcher matcher) {
        return matcher.group(2) != null;
    }

    private void appendResolvedReplacement(Matcher matcher, StringBuffer out) {
        String tokenName = getTokenName(matcher);
        boolean explicitCall = isExplicitCall(matcher);
        String replacement = resolveHtmlMethodBinding(tokenName, explicitCall);
        matcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
    }

    private String resolveHtmlMethodBinding(String name, boolean explicitCall) {
        List<HtmlMethodBindingStrategy> strategies = explicitCall ? explicitCallStrategies : defaultStrategies;
        for (HtmlMethodBindingStrategy strategy : strategies) {
            try {
                String result = strategy.bindToken(controller, name);
                if (result != null) return result;
            } catch (Exception e) {
                // Ignore and try next strategy
            }
        }
        return "";
    }

    public static String stringify(Object value) {
        return value == null ? "" : value.toString();
    }

    private static String readUtf8(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        }
    }
}
