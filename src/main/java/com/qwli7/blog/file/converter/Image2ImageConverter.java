package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;
import com.qwli7.blog.file.vo.ResizeControlArgs;
import com.qwli7.blog.file.vo.WatermarkTextControlArgs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        if(!graphicsMagicAvailable()) {
            logger.error("method<doConvert> gm 不可用");
            return;
        }
        final LinkedList<String> commands = buildCommands(controlArgs);
        final String result = doProcess(commands, getGraphicsMagickPath());
        logger.info("method<doConvert> result: [{}]", result);
    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        LinkedList<String> commands = new LinkedList<>();
        if(controlArgs instanceof ResizeControlArgs) {
            final ResizeControlArgs resizeControlArgs = (ResizeControlArgs) controlArgs;
            commands.add(getGraphicsMagickPath());
            commands.add("-convert");
            commands.add("-resize");
            if(resizeControlArgs.isForceResize()) {
                commands.add(resizeControlArgs.getWidth() + "x" + resizeControlArgs.getHeight() + "!");
            } else {
                if(resizeControlArgs.getWidth() != null && resizeControlArgs.getWidth() != 0) {
                    commands.add(resizeControlArgs.getWidth() + "x");
                }
                if(resizeControlArgs.getHeight() != null && resizeControlArgs.getHeight() > 0) {
                    commands.add("x"+ resizeControlArgs.getHeight());
                }
//            commands.add(resize.getWidth())
            }
            commands.add("-strip");
            commands.add("-quality");
            commands.add(resizeControlArgs.getQuality() + "");
            commands.add(resizeControlArgs.getInputFile().getAbsolutePath());
            commands.add(resizeControlArgs.getOutputFile().getAbsolutePath());
        }
        return commands;
    }
}
