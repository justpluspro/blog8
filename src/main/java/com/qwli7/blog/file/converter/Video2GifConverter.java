package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;

/**
 * 视频转 gif
 * 目标 gif
 */
public class Video2GifConverter extends AbstractMediaConverter {

    public Video2GifConverter(String ffmpegPath) {
        super(ffmpegPath, null);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }
}
