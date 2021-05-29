package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.vo.ControlArgs;
import com.qwli7.blog.file.vo.WatermarkTextControlArgs;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 资源添加水印 converter
 * @author liqiwen
 * @since 2.0
 */
public class WatermarkConverter extends AbstractMediaConverter {

    public WatermarkConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {
        final String action = controlArgs.getAction();
        if(Arrays.asList("video2watermark", "image2watermark").contains(action)) {
            WatermarkTextControlArgs watermarkTextControlArgs = null;
            if(controlArgs instanceof WatermarkTextControlArgs) {
                watermarkTextControlArgs = (WatermarkTextControlArgs) controlArgs;
            }
            if(watermarkTextControlArgs == null) {
                logger.info("method<doConvert> 无效的水印参数");
                return;
            }
            final String text = watermarkTextControlArgs.getText();
            if(StringUtils.isEmpty(text)) {
                logger.info("method<doConvert> 无要添加水印的文本");
                return;
            }
            String processBashPath = "";
            List<String> commands;
            if ("video2watermark".equals(action)) {
                commands = buildAddVideo2WatermarkCommand(sourceFile, targetFile, text);
                processBashPath = getFfmpegPath();
                doProcess(commands, processBashPath);
            } else {
                commands = buildAddImage2WatermarkCommand(sourceFile, targetFile, text);
                processBashPath = getGraphicsMagickPath();
            }
            doProcess(commands, processBashPath);
        }
    }

    @Override
    public LinkedList<String> buildCommands(ControlArgs controlArgs) {
        return null;
    }

    /**
     * 给图片添加水印，生成添加水印命令
     * gm  convert image_2000x3000.jpg -fill white -pointsize 128 -font "C:/Windows/Fonts/STCAIYUN.TTF" -gravity northeast -draw "text 40,120 'bilibili'" dest-c.jpg
     * @param sourceFile sourceFile
     * @param targetFile targetFile
     * @param text text
     * @return List<String>
     */
    private List<String> buildAddImage2WatermarkCommand(File sourceFile, File targetFile, String text) {
        List<String> commands = new ArrayList<>();
        commands.add("convert");
        commands.add(sourceFile.getAbsolutePath());
        commands.add("-fill white");
        commands.add("-pointsize 128");
        commands.add("-font C:/Windows/Fonts/STCAIYUN.TTF");
        commands.add("-gravity northeast");
        commands.add("-draw");
        commands.add("text 40,120");
        commands.add("'");
        commands.add(text.toLowerCase());
        commands.add("'");
        commands.add(targetFile.getAbsolutePath());
        return commands;
    }


    /**
     * 给视频添加文字水印
     * ffmpeg -y -i video.mp4 -vf drawtext=fontfile=arial.ttf:text=bilibili:x=w-tw-10:y=10:fontsize=60:fontcolor=gray output.mp4
     * @param sourceFile  输入文件
     * @param outputFile 输出文件
     * @param text 水印文字
     */
    public List<String> buildAddVideo2WatermarkCommand(File sourceFile, File outputFile, String text) {
        List<String> command = new ArrayList<>();
        command.add(I);
        command.add(sourceFile.getAbsolutePath());
        command.add(FORCE_SAVE);
        command.add("-vf");

        StringBuilder paramsBuilder = new StringBuilder("drawtext=");
        paramsBuilder.append("fontfile='C\\:\\\\Windows\\\\Fonts\\\\STHUPO.TTF'").append(":text=").append(text)
                .append(":x=w-tw-16")
                .append(":y=16")
                .append(":fontsize=60")
                .append(":fontcolor=gray");
        command.add(paramsBuilder.toString());
        command.add(outputFile.getAbsolutePath());
        return command;
    }
}
