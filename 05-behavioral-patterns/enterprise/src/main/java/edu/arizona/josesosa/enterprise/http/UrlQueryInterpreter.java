package edu.arizona.josesosa.enterprise.http;

import java.util.Map;

/**
 * A minimal Interpreter-pattern implementation for URL query strings.
 * Grammar (simplified):
 * Query   := Pair ("&" Pair)*
 * Pair    := key ["=" value]
 * key     := any char except '&' or '='
 * value   := any char except '&'
 * This interpreter produces a Map<String,String> of key/value pairs.
 */
public final class UrlQueryInterpreter {

    public Map<String, String> parse(String rawQuery) {
        UrlQueryParseContext ctx = new UrlQueryParseContext(rawQuery);
        new UrlQueryExpression().interpret(ctx);
        return ctx.result;
    }
}
