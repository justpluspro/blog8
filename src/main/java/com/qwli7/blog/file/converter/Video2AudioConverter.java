package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;

/**
 * 视频转音频
 * @author liqiwen
 * @since 2.2
 */
public class Video2AudioConverter extends AbstractMediaConverter {


    public Video2AudioConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }
}
