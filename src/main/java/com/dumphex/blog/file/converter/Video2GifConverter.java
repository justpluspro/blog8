package com.dumphex.blog.file.converter;

import com.dumphex.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

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

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        return null;
    }
}
