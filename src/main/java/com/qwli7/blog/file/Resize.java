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
     * 取值到 1（最低图像质量和最高压缩率） ~ 100（最高图像质量和最低压缩率）
     */
    private Integer quality;

    /**
     * 是否原样输出
     */
    private boolean keepRate;

    /**
     * 是否强制大小
     */
    private boolean forceResize;

    public boolean isKeepRate() {
        return keepRate;
    }

    public void setKeepRate(boolean keepRate) {
        this.keepRate = keepRate;
    }

    public boolean isForceResize() {
        return forceResize;
    }

    public void setForceResize(boolean forceResize) {
        this.forceResize = forceResize;
    }

    public Resize(Integer size) {
        this.width = size;
        this.height = size;
        this.quality = 1;
    }

    /**
     * quality: 取值到 1（最低图像质量和最高压缩率） ~ 100（最高图像质量和最低压缩率）
     * @param width width
     * @param height height
     * @param keepRate keepRate
     */
    public Resize(Integer width, Integer height, boolean keepRate, boolean forceResize) {
        this.width = width;
        this.height = height;
        this.keepRate = keepRate;
        if(keepRate) {
            // 保持原比例
            this.quality = 100;
        } else {
            // 比例设置成 80
            this.quality = 80;
        }

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

    public boolean isValid() {
        if(width < 0 && height < 0) {
            return false;
        }
        return true;
    }
}
