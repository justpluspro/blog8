package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

/**
 * 视频转图片
 * 目标格式：png
 * 原格式 mp4, rmvb, 3gp, avi 等
 */
public class Video2ImageConverter extends AbstractMediaConverter {

    public Video2ImageConverter(String ffmpegPath) {
        super(ffmpegPath, null);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }

    @Override
    public LinkedList<String> buildCommands() {
        return null;
    }
}
