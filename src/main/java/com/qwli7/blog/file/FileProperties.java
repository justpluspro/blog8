package com.qwli7.blog.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2021/3/2 8:33
 * 功能：blog
 **/
@Configuration
@ConfigurationProperties(prefix = "blog.file")
@PropertySource(value = "classpath:file.properties")
public class FileProperties {

    private String uploadPath;

    private String uploadThumbPath;

    public String getUploadThumbPath() {
        return uploadThumbPath;
    }

    public void setUploadThumbPath(String uploadThumbPath) {
        this.uploadThumbPath = uploadThumbPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
