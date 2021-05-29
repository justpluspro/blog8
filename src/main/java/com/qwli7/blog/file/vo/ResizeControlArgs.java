package com.qwli7.blog.file.vo;

/**
 * 缩放控制参数
 * @author liqiwen
 * @since 2.4
 */
public class ResizeControlArgs extends ControlArgs {

    private boolean forceResize;

    private Integer width;

    private Integer height;

    private Integer quality;

    public boolean isForceResize() {
        return forceResize;
    }

    public void setForceResize(boolean forceResize) {
        this.forceResize = forceResize;
    }

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
}
