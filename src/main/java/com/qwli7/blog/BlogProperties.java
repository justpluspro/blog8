package com.qwli7.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 博客属性配置
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
@ConfigurationProperties(prefix = "blog.core")
@PropertySource(value = "classpath:blog.properties")
@Configuration
public class BlogProperties {

    /**
     * MarkdownServerUrl
     * 基于 NodeJs
     */
    private String markdownServerUrl;

    private String ipHeader;

    private String tokenHeader;

    private int defaultPageSize;

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public void setMarkdownServerUrl(String markdownServerUrl) {
        this.markdownServerUrl = markdownServerUrl;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public String getMarkdownServerUrl() {
        return markdownServerUrl;
    }


    public String getIpHeader() {
        return ipHeader;
    }

    public void setIpHeader(String ipHeader) {
        this.ipHeader = ipHeader;
    }
}
