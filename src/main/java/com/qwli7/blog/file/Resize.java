package com.qwli7.blog.file;

/**
 * 缩放参数
 * @author liqiwen
 * @since 2.1
 */
public class Resize {

    private Integer width;

    private Integer height;

    /**
     * 缩放质量
     */
    private Integer quality;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public boolean isValid() {
        return true;
    }
}
