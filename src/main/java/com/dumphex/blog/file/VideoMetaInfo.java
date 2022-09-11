package com.dumphex.blog.file;

import java.io.Serializable;

/**
 * @author liqiwen
 * @since 1.2
 * 视频信息
 */
public class VideoMetaInfo implements Serializable {

    /**
     * 视频宽度
     */
    private final int width;

    /**
     * 视频高度
     */
    private final int height;
    /**
     * 视频持续时长
     */
    private final int duration;

    /**
     * 视频大小
     */
    private long size;

    /**
     * 视频扩展名
     */
    private final String extension;
  
    public VideoMetaInfo(int width, int height, int duration, String extension) {
        super();
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.extension = extension;
   }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public int getWidth() {
    return width;
  }
  
   public int getHeight() {
    return height; 
  }
  
   public int getDuration() {
    return duration;
  } 
}
