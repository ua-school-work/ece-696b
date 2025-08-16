package edu.arizona.josesosa.enterprise.http;

final class UrlQueryExpression implements UrlQueryAstNode {
    private final UrlQueryAstNode pair = new UrlQueryPairExpression();

    @Override
    public void interpret(UrlQueryParseContext ctx) {
        if (!ctx.hasNext()) return;
        pair.interpret(ctx);
        while (ctx.hasNext()) {
            if (ctx.peek() == '&') {
                ctx.next(); // consume '&'
                // allow empty segments like trailing '&'
                if (ctx.hasNext()) {
                    pair.interpret(ctx);
                }
            } else {
                // Unexpected char; consume to avoid infinite loop
                ctx.next();
            }
        }
    }
}
