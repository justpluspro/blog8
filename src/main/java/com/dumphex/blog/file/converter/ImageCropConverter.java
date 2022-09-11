package com.dumphex.blog.file.converter;

import com.dumphex.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

/**
 * 图像裁剪
 * @author liqiwen
 */
public class ImageCropConverter extends AbstractMediaConverter {


    public ImageCropConverter(String ffmpegPath, String graphicsMagickPath) {
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
