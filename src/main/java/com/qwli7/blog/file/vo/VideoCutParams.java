package com.qwli7.blog.file.vo;

import java.io.File;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * 视频截图参数
 * @author liqiwen
 * @since 2.0
 */
public class VideoCutParams implements Serializable {

    /**
     * 输入文件
     */
    private File inputFile;

    /**
     * 从什么时候开始截图
     */
    private LocalTime localTime;

    /**
     * 缩略图图片存放路径
     */
    private String path;

    /**
     * 截图宽度
     */
    private Integer width;

    /**
     * 截图高度
     */
    private Integer height;

    /**
     *  截取的视频帧的时长（从开始时间算，单位 s，需要小于原视频的最大时长）
     */
    private int timeLength;

    /**
     * false - 静态图（只截取 time 时间点的那一帧图片）true，动态图，（截取时间从 time 时间开始，timeLength 这段时间内的多张图）
     */
    private boolean isContinuous;

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public void setContinuous(boolean continuous) {
        isContinuous = continuous;
    }
}
