package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;

/**
 * 抽取元数据信息
 * @author liqiwen
 * @since 2.0
 */
public class ExtractMetaConverter extends AbstractMediaConverter {

    public ExtractMetaConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }
}
