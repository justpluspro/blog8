package com.qwli7.blog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.service.Markdown2Html;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultMarkdown2Html implements Markdown2Html {

    private final Markdown2Html delegate;

    public DefaultMarkdown2Html(BlogProperties blogProperties) {
        final String markdownServerUrl = blogProperties.getMarkdownServerUrl();
        if(StringUtils.isEmpty(blogProperties.getMarkdownServerUrl())) {
            this.delegate = new CommonMarkdown2Html();
        } else {
            this.delegate = new MarkdownConverter(blogProperties.getMarkdownServerUrl());
        }
    }

    @Override
    public Map<Integer, String> toHtmls(Map<Integer, String> markdownMap) {
        return delegate.toHtmls(markdownMap);
    }

    @Override
    public String toHtml(String markdown) {
        return delegate.toHtml(markdown);
    }

    static class CommonMarkdown2Html implements Markdown2Html {

        private final HtmlRenderer renderer;
        private final Parser parser;

        public CommonMarkdown2Html() {
            List<Extension> extensionList = Arrays.asList(AutolinkExtension.create(), TablesExtension.create(), HeadingAnchorExtension.create());
            this.parser = Parser.builder().extensions(extensionList).build();
            this.renderer = HtmlRenderer.builder().extensions(extensionList).build();
        }

        @Override
        public Map<Integer, String> toHtmls(Map<Integer, String> markdownMap) {
            Map<Integer, String> map = new HashMap<>();
            for(Map.Entry<Integer, String> it: markdownMap.entrySet()) {
                map.put(it.getKey(), toHtml(it.getValue()));
            }
            return map;
        }

        @Override
        public String toHtml(String markdown) {
            if(StringUtils.isEmpty(markdown)){
                return "";
            }
            Node document = parser.parse(markdown);
            return renderer.render(document);
        }
    }

    static class MarkdownConverter implements Markdown2Html {

        private String serverUrl;

        public MarkdownConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        @Override
        public Map<Integer, String> toHtmls(Map<Integer, String> markdownMap) {
            return null;
        }

        @Override
        public String toHtml(String markdown) {
            return null;
        }
    }
}
