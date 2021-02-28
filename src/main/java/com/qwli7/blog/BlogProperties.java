package com.qwli7.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "blog.core")
@Configuration
public class BlogProperties {

    private String markdownServerUrl;

    public String getMarkdownServerUrl() {
        return markdownServerUrl;
    }
}
