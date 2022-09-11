package com.dumphex.blog.file.converter;

import com.dumphex.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

/**
 * 视频裁剪
 * @author liqiwen
 * @since 2.2
 */
public class VideoCropConverter extends AbstractMediaConverter {


    public VideoCropConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        return null;
    }
}
