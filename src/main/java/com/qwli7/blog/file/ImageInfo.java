package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2021/3/2 8:30
 * 功能：blog
 **/
public class ImageInfo implements Serializable {
    private final int width;

    private final int height;

    private final String type;

    public ImageInfo(int width, int height, String type) {
        super();
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }
}
