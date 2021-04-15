package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.util.WebUtils;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.*;
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

    public DefaultMarkdown2Html(BlogProperties blogProperties, RestTemplate restTemplate) {
        final String markdownServerUrl = blogProperties.getMarkdownServerUrl();
        if(StringUtils.isEmpty(markdownServerUrl)) {
            this.delegate = new CommonMarkdown2Html();
        } else {
            if(WebUtils.isRegularUrl(markdownServerUrl)) {
                throw new RuntimeException("The node service is configured, but the service url is not regular, please check");
            }
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
            List<Extension> extensionList = Arrays.asList(AutolinkExtension.create(),
                    TablesExtension.create(),
                    HeadingAnchorExtension.create());
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

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(markdown, headers);
            final ResponseEntity<NodeResult> responseEntity = restTemplate.postForEntity(
                    serverUrl, httpEntity, NodeResult.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()) {
                final NodeResult nodeResult = responseEntity.getBody();
                if(nodeResult != null && nodeResult.isSuccess()) {
                    return nodeResult.getData();
                }
            }
            return "";
        }
    }

    static class NodeResult  {

        public static final Integer SUCCESS = 200;

        private Integer code;

        private String data;

        private String msg;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isSuccess() {
            return getCode() != null && getCode().equals(SUCCESS);
        }
    }
}
