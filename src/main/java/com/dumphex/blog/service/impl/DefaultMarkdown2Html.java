package com.dumphex.blog.service.impl;

import com.dumphex.blog.service.Markdown2Html;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Markdown2Html 解析器
 * @author liqiwen
 * @since 1.2
 */
@Component
public class DefaultMarkdown2Html implements Markdown2Html {
    /**
     * 解析 Markdown 的代理
     */
    private final Markdown2Html delegate;

    public DefaultMarkdown2Html() {
        this.delegate = new CommonMarkdown2Html();
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
            List<Extension> extensionList = Arrays.asList(AutolinkExtension.create(),
                    TablesExtension.create(),
                    HeadingAnchorExtension.create());
            this.parser = Parser.builder().extensions(extensionList).build();
            this.renderer = HtmlRenderer.builder()
                    .attributeProviderFactory((context) -> new ImageAttributeProvider())
                    .extensions(extensionList).build();
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

    static class ImageAttributeProvider implements AttributeProvider {

        @Override
        public void setAttributes(Node node, String s, Map<String, String> attributes) {
            if(node instanceof Image) {
                attributes.put("class", "border");
            }
        }
    }
}
