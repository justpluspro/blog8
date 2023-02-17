package com.qwli7.blog.plugin.md;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2023/2/17 9:24
 * 功能：blog8
 **/
@Component
public class DefaultMarkdownHandler implements MarkdownHandler {

    private final HtmlRenderer htmlRenderer;
    private final Parser parser;

    private DefaultMarkdownHandler() {
        parser = Parser.builder().build();
        htmlRenderer = HtmlRenderer.builder().build();
    }


    @Override
    public String toHtml(String markdown) {
        Node node = parser.parse(markdown);
        return htmlRenderer.render(node);
    }
}
