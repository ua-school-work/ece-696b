package edu.arizona.josesosa.enterprise.http;

final class UrlQueryPairExpression implements UrlQueryAstNode {
    @Override
    public void interpret(UrlQueryParseContext ctx) {
        StringBuilder key = new StringBuilder();
        StringBuilder val = new StringBuilder();
        boolean hasEquals = false;
        while (ctx.hasNext()) {
            char c = ctx.peek();
            if (c == '&') {
                break;
            } else if (c == '=' && !hasEquals) {
                hasEquals = true;
                ctx.next(); // consume '='
            } else {
                if (!hasEquals) key.append(c);
                else val.append(c);
                ctx.next();
            }
        }
        String k = HttpSupport.urlDecode(key.toString());
        String v = hasEquals ? HttpSupport.urlDecode(val.toString()) : "";
        if (!k.isBlank()) {
            ctx.result.put(k, v);
        }
    }
}
