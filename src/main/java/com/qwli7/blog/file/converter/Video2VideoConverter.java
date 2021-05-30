package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;

import java.io.File;
import java.util.LinkedList;

/**
 * 视频转视频
 * 目标视频 mp4
 * 原视频格式 avi, rmvb, mov, 3gp 等等视频格式
 */
public class Video2VideoConverter extends AbstractMediaConverter {

    public Video2VideoConverter(String ffmpegPath) {
        super(ffmpegPath, null);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        LinkedList<String> commands = new LinkedList<>();
        commands.add(getFfmpegPath());
        commands.add("-i");
        commands.add(controlArgs.getInputFile().getAbsolutePath());






        return commands;
    }
}
