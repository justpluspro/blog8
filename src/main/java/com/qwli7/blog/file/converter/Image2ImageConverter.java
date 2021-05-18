package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

/**
 * 图片转图片
 * @author liqiwen
 * @since 2.0
 */
public class Image2ImageConverter extends AbstractMediaConverter {

    public Image2ImageConverter(String gmPath) {
        super(null, gmPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }

    @Override
    public LinkedList<String> buildCommands() {
        return null;
    }
}
