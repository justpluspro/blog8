package com.qwli7.blog.plugin.file;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 13:53
 * 功能：blog8
 **/
public class Resize implements Serializable {

    public Resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private int width;

    private int height;

    /**
     * 是否等比例缩放
     */
    private boolean scaleRatio;

    /**
     * 是否强制缩放
     */
    private boolean forceScale;

    public boolean isScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(boolean scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public boolean isForceScale() {
        return forceScale;
    }

    public void setForceScale(boolean forceScale) {
        this.forceScale = forceScale;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
