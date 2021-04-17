package com.qwli7.blog.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author qwli7
 * 2021/3/2 8:33
 * 功能：FileProperties
 **/
@Conditional(FileCondition.class)
@Configuration
@ConfigurationProperties(prefix = "blog.file")
@PropertySource(value = "classpath:blog.properties")
public class FileProperties {

    /**
     * 上传路径
     */
    private String uploadPath;

    /**
     * 缩略图路径
     */
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
