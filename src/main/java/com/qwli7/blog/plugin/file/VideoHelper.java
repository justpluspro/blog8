package com.qwli7.blog.plugin.file;

import com.qwli7.blog.plugin.process.ProcessResult;
import com.qwli7.blog.plugin.process.ProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/21 9:56
 * 功能：blog8
 **/
public class VideoHelper {

    private static final Logger logger = LoggerFactory.getLogger(VideoHelper.class);

    private VideoHelper() {
        super();
    }


    /**
     * ffmpeg -i /mnt/WD1/Temp/VID.mp4 -b 500k -s 960x540 -r 29.5 -ac 1 -ar 44100 -ab 64k -c:v libx264 -y /mnt/WD1/Temp/VID-1.mp4
     * @param filePath filePath
     */
    public static Path convertAndCompress(Path filePath) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(filePath.toString());
//        command.add("-b");
//        command.add("500k");
        command.add("-s");
        command.add("960x540");
        command.add("-r");
        command.add("29.5");
        command.add("-ac");
        command.add("1");
        command.add("-ar");
        command.add("44100");
        command.add("-ab");
        command.add("64k");
        command.add("-c:v");
        command.add("libx264");
        command.add("-y");
        String baseName = FileUtils.getBaseName(filePath);
        Path outputPath = Paths.get(filePath.getParent().toString(), baseName + ".mp4");
        command.add(outputPath.toString());

        try {
            ProcessUtils.executeCmd(command, 0);
        } catch (Exception e) {
            logger.error("command execute failed. ", e);
        }
        return outputPath;
    }


    /**
     * 获取封面图
     * ffmpeg -ss 00:00:10 -i test1.flv -f image2 -y test1.jpg
     */
    public static void getVideoPoster(Path filePath) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add("00:00:00");
        command.add("-i");
        command.add(filePath.toAbsolutePath().toString());
        command.add("-f");
        command.add("image2");
        command.add("-y");
        command.add("test1.jpg");
        try {
            ProcessUtils.executeCmd(command, 0);
        } catch (IOException e) {
            logger.error("command execute failed. ", e);
        }
    }
}
