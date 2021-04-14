package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * 文件更新
 * @author liqiwen
 * @since 2.0
 */
public class FileUpdated implements Serializable {
    /**
     * 文件路径
     */
    private String path;

    /**
     * 待更新的内容
     */
    private String content;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
