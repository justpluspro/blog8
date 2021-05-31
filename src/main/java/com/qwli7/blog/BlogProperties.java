package com.qwli7.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 博客属性配置
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
@Configuration
@ConfigurationProperties(prefix = "blog.core")
public class BlogProperties {

    private String markdownServerUrl;

    private String ipHeader;

    private String tokenHeader;

    private int defaultPageSize;

    private int maxArticleTagSize;

    private String urlPrefix;

    private boolean backup;

    public boolean isBackup() {
        return backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public int getMaxArticleTagSize() {
        return maxArticleTagSize;
    }

    public void setMaxArticleTagSize(int maxArticleTagSize) {
        this.maxArticleTagSize = maxArticleTagSize;
    }

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
