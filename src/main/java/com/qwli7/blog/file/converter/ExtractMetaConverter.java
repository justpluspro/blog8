package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.VideoMetaInfo;
import com.qwli7.blog.file.vo.ControlArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 抽取元数据信息
 * @author liqiwen
 * @since 2.0
 */
public class ExtractMetaConverter extends AbstractMediaConverter {


    /**
     * 视频时长正则匹配式
     * 用于解析视频及音频的时长等信息时使用；
     * (.*?)表示：匹配任何除\r\n之外的任何0或多个字符，非贪婪模式
     */
    private final String DURATION_REGEX = "Duration: (\\d*?):(\\d*?):(\\d*?)\\.(\\d*?), start: (.*?), bitrate: (\\d*) kb/s.*";
    private final Pattern DURATION_PATTERN = Pattern.compile(DURATION_REGEX);

    /**
     * 视频流信息正则匹配式
     * 用于解析视频详细信息时使用；
     */
    private final String VIDEO_STREAM_REGEX = "Stream #\\d:\\d[(]??\\S*[)]??: Video: (\\S*\\S$?)[^,]*, (.*?), (\\d*)x(\\d*)[^,]*, (\\d*) kb/s, (\\d*[.]??\\d*) fps";
    private final Pattern VIDEO_STREAM_PATTERN = Pattern.compile(VIDEO_STREAM_REGEX);

    /**
     * 音频流信息正则匹配式
     * 用于解析音频详细信息时使用；
     */
    private final String MUSIC_STREAM_REGEX = "Stream #\\d:\\d[(]??\\S*[)]??: Audio: (\\S*\\S$?)(.*), (.*?) Hz, (.*?), (.*?), (\\d*) kb/s";;
    private final Pattern MUSIC_STREAM_PATTERN = Pattern.compile(MUSIC_STREAM_REGEX);

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public ExtractMetaConverter(String ffmpegPath, String graphicsMagickPath) {
        super(ffmpegPath, graphicsMagickPath);
    }

    @Override
    public void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs) {

    }

    @Override
    public LinkedList<String> buildCommands() {
        return null;
    }


    private List<String> buildVideoMetaInfoCommand(File sourceFile) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(sourceFile.getAbsolutePath());
        return commands;
    }


    private void extractVideoMetaInfo(File sourceFile) {

        final List<String> commands = buildVideoMetaInfoCommand(sourceFile);
        final String resultStr = doProcess(commands, getFfmpegPath());

        Matcher matcher = DURATION_PATTERN.matcher(resultStr);
        Matcher videoStreamMatcher = VIDEO_STREAM_PATTERN.matcher(resultStr);

        // 视频持续时长
        long duration = 0L;
        //视频码率
        int videoBitRate;
        //视频大小
        long videoSize = sourceFile.length();
        //视频格式
        String format = StringUtils.getFilename(sourceFile.getAbsolutePath());

        //视频编码器
        String videoEncoder;
        Integer videoHeight = 0;
        int videoWidth = 0;
        //视频帧率
        Float videoFramerate;

        //音频格式
        String musicFormat = "";
        // 音频采样率
        long sampleRate = 0L;
        //音频码率
        Integer musicBitRate = 0;

        if(matcher.find()) {
            //小时
            long hours = Integer.parseInt(matcher.group(1));
            //分钟
            long minutes = Integer.parseInt(matcher.group(2));
            //秒数
            long seconds = Integer.parseInt(matcher.group(3));
            long dec = Integer.parseInt(matcher.group(4));
            duration = dec * 100L + seconds * 1000L + minutes * 60L * 1000L + hours * 60L * 60L * 1000L;
            videoBitRate = Integer.parseInt(matcher.group(6));
        }

        if(videoStreamMatcher.find()) {
            videoEncoder = videoStreamMatcher.group(1);
            String s2 = videoStreamMatcher.group(2);
            videoWidth = Integer.parseInt(videoStreamMatcher.group(3));
            videoHeight = Integer.parseInt(videoStreamMatcher.group(4));
            String s5 = videoStreamMatcher.group(5);
            videoFramerate = Float.parseFloat(videoStreamMatcher.group(6));
            System.out.println(s2);
            System.out.println(videoWidth);
            System.out.println(videoHeight);
            System.out.println(s5);
            System.out.println(videoFramerate);
        }

        VideoMetaInfo videoMetaInfo = new VideoMetaInfo(videoWidth, videoHeight,
                Math.toIntExact(duration), format);
        videoMetaInfo.setSize(videoSize);
    }
}
