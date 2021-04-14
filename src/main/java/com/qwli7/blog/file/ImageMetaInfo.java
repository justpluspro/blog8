package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * @author qwli7 
 * 2021/3/2 8:30
 * 功能：图片信息
 **/
public class ImageMetaInfo implements Serializable {

    /**
     * 图片宽度
     */
    private final int width;

    /**
     * 图片高度
     */
    private final int height;

    /**
     * 图片大小
     */
    private final long size;

    /**
     * 图片类型
     */
    private final String extension;

    public ImageMetaInfo(int width, int height, long size, String extension) {
        super();
        this.width = width;
        this.height = height;
        this.extension = extension;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getExtension() {
        return extension;
    }
}
