package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.service.Markdown2Html;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Markdown2Html 解析器
 * @author liqiwen
 * @since 1.2
 */
@Component
public class DefaultMarkdown2Html implements Markdown2Html {

    private final Markdown2Html delegate;
    private RestTemplate restTemplate;

    public DefaultMarkdown2Html(BlogProperties blogProperties, RestTemplate restTemplate) {
        final String markdownServerUrl = blogProperties.getMarkdownServerUrl();
        if(StringUtils.isEmpty(markdownServerUrl)) {
            this.delegate = new CommonMarkdown2Html();
        } else {
            this.delegate = new MarkdownConverter(markdownServerUrl, restTemplate);
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

    /**
     * CommonMarkdown 解析器
     */
    public static class CommonMarkdown2Html implements Markdown2Html {

        private final HtmlRenderer renderer;
        private final Parser parser;

        public CommonMarkdown2Html() {
            List<Extension> extensionList = Arrays.asList(AutolinkExtension.create(), TablesExtension.create(), HeadingAnchorExtension.create());
            this.parser = Parser.builder().extensions(extensionList).build();
            this.renderer = HtmlRenderer.builder().extensions(extensionList).build();
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

    /**
     * NodeJs  marked markdown 解析器
     */
    public static class MarkdownConverter implements Markdown2Html {

        private final String serverUrl;
        private final RestTemplate restTemplate;

        public MarkdownConverter(String serverUrl, RestTemplate restTemplate) {
            this.serverUrl = serverUrl;
            this.restTemplate = restTemplate;
        }

        @Override
        public String toHtml(String markdown) {
            if(StringUtils.isEmpty(markdown)) {
                return "";
            }
            return restTemplate.execute(serverUrl, HttpMethod.POST,
                    null, null, markdown);
        }
    }
}
