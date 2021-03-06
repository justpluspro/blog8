package com.qwli7.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "blog.core")
@PropertySource(value = "classpath:file.properties")
@Configuration
public class BlogProperties {

    private String markdownServerUrl;

    private int defaultPageSize;

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
}
