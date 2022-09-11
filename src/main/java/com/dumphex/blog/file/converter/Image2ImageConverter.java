package com.dumphex.blog.file.converter;

import com.dumphex.blog.file.Resize;
import com.dumphex.blog.file.vo.ControlArgs;
import com.dumphex.blog.file.vo.ResizeControlArgs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        if(!graphicsMagicAvailable()) {
            logger.error("method<doConvert> gm 不可用");
            try {
                Files.deleteIfExists(targetFile.toPath());
            } catch (IOException ex){
              logger.info("静默删除目标文件: [{}]", targetFile);
            }
            return;
        }
        final LinkedList<String> commands = buildCommands(controlArgs);
        final String result = doProcess(commands);
        logger.info("method<doConvert> result: [{}]", result);
    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        LinkedList<String> commands = new LinkedList<>();
        if(controlArgs instanceof ResizeControlArgs) {
            final ResizeControlArgs resizeControlArgs = (ResizeControlArgs) controlArgs;
            final Resize resize = resizeControlArgs.getResize();
            commands.add(getGraphicsMagickPath());
            commands.add("-convert");
            commands.add("-resize");
            if(resize.isForceResize()) {
                commands.add(resize.getWidth() + "x" + resize.getHeight() + "!");
            } else {
                if(resize.getWidth() != null && resize.getHeight() != null) {
                    commands.add(resize.getWidth() + "x" + resize.getHeight());
                } else if(resize.getWidth() != null && resize.getWidth() > 0) {
                    commands.add(resize.getWidth() + "x");
                } else if(resize.getHeight() != null && resize.getHeight() > 0) {
                    commands.add("x"+ resize.getHeight());
                } else {
                    // impossible branch
                }
            }
            commands.add("-strip");
            commands.add("-quality");
            commands.add(resize.getQuality() + "");
            commands.add(resizeControlArgs.getInputFile().getAbsolutePath());
            commands.add(resizeControlArgs.getOutputFile().getAbsolutePath());
        }
        return commands;
    }
}
