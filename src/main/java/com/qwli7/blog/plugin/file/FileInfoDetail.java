package com.qwli7.blog.plugin.file;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/21 11:45
 * 功能：blog8
 **/
public class FileInfoDetail extends FileInfo implements Serializable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
