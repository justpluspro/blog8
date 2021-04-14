package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * 音频信息
 * @author liqiwen
 * @since 2.0
 */
public class AudioMetaInfo implements Serializable {

    private String ext;

    private long size;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
