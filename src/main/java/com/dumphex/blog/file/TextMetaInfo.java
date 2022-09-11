package com.dumphex.blog.file;

import java.io.Serializable;

/**
 * 文本元数据信息
 * @author liqiwen
 * @since 2.0
 */
public class TextMetaInfo implements Serializable {

    private String ext;

    private String path;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
