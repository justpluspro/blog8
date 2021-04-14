package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * @author qwli7
 * 2021/3/2 8:46
 * 功能：FileInfoDetail
 **/
public class FileInfoDetail implements Serializable {

    private int height;

    private int width;

    private String ext;

    private String path;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

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
