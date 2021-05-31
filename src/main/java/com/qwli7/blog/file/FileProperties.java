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
public class FileProperties {

    private String uploadPath;
    private String uploadThumbPath;
    private int totalSem;
    private String ffmpegPath;
    private String ffprobePath;
    private String graphicsMagickPath;

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public String getGraphicsMagickPath() {
        return graphicsMagickPath;
    }

    public String getFfprobePath() {
        return ffprobePath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public void setFfprobePath(String ffprobePath) {
        this.ffprobePath = ffprobePath;
    }

    public void setGraphicsMagickPath(String graphicsMagickPath) {
        this.graphicsMagickPath = graphicsMagickPath;
    }

    public int getTotalSem() {
        return totalSem;
    }

    public void setTotalSem(int totalSem) {
        this.totalSem = totalSem;
    }

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
