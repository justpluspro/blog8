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


    /**
     * 利用 ffprobe 获取数据原信息
     * ffprobe -v quiet -print_format json -show_format -show_streams /Users/liqiwen/Desktop/video.mp4
     * ffprobe -loglevel quiet -show_format -show_streams /Users/liqiwen/Desktop/video.mp4 -print_format json
     * @param sourceFile sourceFile
     * @return commands
     */
    private List<String> createMetaInfoCommandUseFfprobe(File sourceFile) {
        List<String> commands = new ArrayList<>();
        commands.add("-loglevel");
        commands.add("quiet");
        commands.add("-show_format");
        commands.add("-show_streams");
        commands.add(sourceFile.getAbsolutePath());
        commands.add("-print_format");
        commands.add("json");

        return commands;
    }


    /**
     * 提取元数据信息使用 ffprobe
     * @param sourceFile sourceFile
     *       {
     *     "streams": [
     *         {
     *             "index": 0,
     *             "codec_name": "h264",
     *             "codec_long_name": "H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10",
     *             "profile": "High",
     *             "codec_type": "video",
     *             "codec_time_base": "1/50",
     *             "codec_tag_string": "avc1",
     *             "codec_tag": "0x31637661",
     *             "width": 1280,
     *             "height": 720,
     *             "coded_width": 1280,
     *             "coded_height": 720,
     *             "closed_captions": 0,
     *             "has_b_frames": 2,
     *             "pix_fmt": "yuv420p",
     *             "level": 50,
     *             "chroma_location": "left",
     *             "refs": 1,
     *             "is_avc": "true",
     *             "nal_length_size": "4",
     *             "r_frame_rate": "25/1",
     *             "avg_frame_rate": "25/1",
     *             "time_base": "1/12800",
     *             "start_pts": 0,
     *             "start_time": "0.000000",
     *             "duration_ts": 539136,
     *             "duration": "42.120000",
     *             "bit_rate": "1384716",
     *             "bits_per_raw_sample": "8",
     *             "nb_frames": "1053",
     *             "disposition": {
     *                 "default": 1,
     *                 "dub": 0,
     *                 "original": 0,
     *                 "comment": 0,
     *                 "lyrics": 0,
     *                 "karaoke": 0,
     *                 "forced": 0,
     *                 "hearing_impaired": 0,
     *                 "visual_impaired": 0,
     *                 "clean_effects": 0,
     *                 "attached_pic": 0,
     *                 "timed_thumbnails": 0
     *             },
     *             "tags": {
     *                 "language": "und",
     *                 "handler_name": "VideoHandler"
     *             }
     *         },
     *         {
     *             "index": 1,
     *             "codec_name": "aac",
     *             "codec_long_name": "AAC (Advanced Audio Coding)",
     *             "profile": "LC",
     *             "codec_type": "audio",
     *             "codec_time_base": "1/44100",
     *             "codec_tag_string": "mp4a",
     *             "codec_tag": "0x6134706d",
     *             "sample_fmt": "fltp",
     *             "sample_rate": "44100",
     *             "channels": 2,
     *             "channel_layout": "stereo",
     *             "bits_per_sample": 0,
     *             "r_frame_rate": "0/0",
     *             "avg_frame_rate": "0/0",
     *             "time_base": "1/44100",
     *             "start_pts": 0,
     *             "start_time": "0.000000",
     *             "duration_ts": 1852421,
     *             "duration": "42.005011",
     *             "bit_rate": "129085",
     *             "max_bit_rate": "129085",
     *             "nb_frames": "1810",
     *             "disposition": {
     *                 "default": 1,
     *                 "dub": 0,
     *                 "original": 0,
     *                 "comment": 0,
     *                 "lyrics": 0,
     *                 "karaoke": 0,
     *                 "forced": 0,
     *                 "hearing_impaired": 0,
     *                 "visual_impaired": 0,
     *                 "clean_effects": 0,
     *                 "attached_pic": 0,
     *                 "timed_thumbnails": 0
     *             },
     *             "tags": {
     *                 "language": "eng",
     *                 "handler_name": "SoundHandler"
     *             }
     *         }
     *     ],
     *     "format": {
     *         "filename": "/Users/liqiwen/Desktop/video.mp4",
     *         "nb_streams": 2,
     *         "nb_programs": 0,
     *         "format_name": "mov,mp4,m4a,3gp,3g2,mj2",
     *         "format_long_name": "QuickTime / MOV",
     *         "start_time": "0.000000",
     *         "duration": "42.120000",
     *         "size": "8005082",
     *         "bit_rate": "1520433",
     *         "probe_score": 100,
     *         "tags": {
     *             "major_brand": "isom",
     *             "minor_version": "512",
     *             "compatible_brands": "isomiso2avc1mp41",
     *             "encoder": "Lavf58.51.101"
     *         }
     *     }
     * }
     */
    private void extractMetaInfoUseFfprobe(File sourceFile) {
        final List<String> commands = createMetaInfoCommandUseFfprobe(sourceFile);
        final String resultStr = doProcess(commands, getFfprobePath());
        logger.info("resultStr: [{}]", resultStr);

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
