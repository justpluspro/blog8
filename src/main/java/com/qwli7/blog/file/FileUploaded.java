package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * 文件上传完成
 * @author liqiwen
 * @since 1.2
 */
public class FileUploaded implements Serializable {

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件 url
     */
    private String url;

    /**
     * 文件存储路径
     */
    private String path;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
