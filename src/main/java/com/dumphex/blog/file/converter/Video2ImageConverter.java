package com.dumphex.blog.file.converter;

import com.dumphex.blog.file.vo.ControlArgs;
import com.dumphex.blog.file.vo.ResizeControlArgs;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;

/**
 * 视频转图片
 * 目标格式：png
 * 原格式 mp4, rmvb, 3gp, avi 等
 */
public class Video2ImageConverter extends AbstractMediaConverter {

    private final Image2ImageConverter image2ImageConverter;

    public Video2ImageConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
        image2ImageConverter = new Image2ImageConverter(graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {
        if(!ffmpegAvailable()) {
            logger.error("method<doConvert> ffpmeg 不可用");
            return;
        }
        final LinkedList<String> commands = buildCommands(controlArgs);
        final String result = doProcess(commands);
        logger.info("method<doResult> result: [{}]", result);
        // 旧文件名称
        final String name = targetFile.getName();
        final Path parent = targetFile.toPath().getParent();

        try {
            final Path tempTargetFile = Files.createTempFile(parent, "_tmp$", name);
            image2ImageConverter.convert(targetFile, tempTargetFile.toFile(), controlArgs);
            Files.move(tempTargetFile, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempTargetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        LinkedList<String> commands = new LinkedList<>();
        if(controlArgs instanceof ResizeControlArgs) {
            final ResizeControlArgs resizeControlArgs = (ResizeControlArgs) controlArgs;
            commands.add(getFfmpegPath());
            commands.add("-ss");
            commands.add("00:01");
            commands.add("-i");
            commands.add(resizeControlArgs.getInputFile().getAbsolutePath());
            commands.add("-vframes");
            commands.add("1");
            commands.add("-y");
            commands.add(resizeControlArgs.getOutputFile().getAbsolutePath());
        }
        return commands;
    }
}
