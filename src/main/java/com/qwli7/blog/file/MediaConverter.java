package com.qwli7.blog.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 媒体转换工具
 * 视频：依赖 Ffmpeg
 * 1. 截图
 * 2. 格式转换
 * 3. 加水印
 * 4. 压缩
 *
 * 图片： 依赖 graphicsMagick
 * 1. 缩放
 * 2. 裁剪
 * 3. 加水印
 * @author qwli7
 * @since 2.0
 */
public class MediaConverter implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(MediaConverter.class.getName());
    /**
     * ffmpeg 路径
     */
    private static String ffmpegPath = "/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin/ffmpeg";

    /**
     * ffprobe 路径
     */
    private static String ffprobePath = "/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin/ffprobe";

    /**
     * graphics Magick 路径
     */
    private static String graphicsMagickPath = "/Users/liqiwen/develop/graphics-magick/gm";

    /**
     * 视频时长正则匹配式
     * 用于解析视频及音频的时长等信息时使用；
     *
     * (.*?)表示：匹配任何除\r\n之外的任何0或多个字符，非贪婪模式
     *
     */
    private static final String DURATION_REGEX = "Duration: (\\d*?):(\\d*?):(\\d*?)\\.(\\d*?), start: (.*?), bitrate: (\\d*) kb/s.*";
    private static final Pattern DURATION_PATTERN;


    /**
     * 视频流信息正则匹配式
     * 用于解析视频详细信息时使用；
     */
    private static final String VIDEO_STREAM_REGEX = "Stream #\\d:\\d[(]??\\S*[)]??: Video: (\\S*\\S$?)[^,]*, (.*?), (\\d*)x(\\d*)[^,]*, (\\d*) kb/s, (\\d*[.]??\\d*) fps";
    private static final Pattern VIDEO_STREAM_PATTERN;

    /**
     * 音频流信息正则匹配式
     * 用于解析音频详细信息时使用；
     */
    private static final String MUSIC_STREAM_REGEX = "Stream #\\d:\\d[(]??\\S*[)]??: Audio: (\\S*\\S$?)(.*), (.*?) Hz, (.*?), (.*?), (\\d*) kb/s";;
    private static final Pattern MUSIC_STREAM_PATTERN;

    static {
        DURATION_PATTERN = Pattern.compile(DURATION_REGEX);
        VIDEO_STREAM_PATTERN = Pattern.compile(VIDEO_STREAM_REGEX);
        MUSIC_STREAM_PATTERN = Pattern.compile(MUSIC_STREAM_REGEX);
    }

    /**
     * 获取 gm 执行路径
     * @return graphicsMagickPath
     */
    public static String getGraphicsMagickPath() {
        return graphicsMagickPath;
    }

    /**
     * 设置 gm 执行路径
     * @param graphicsMagickPath graphicsMagickPath
     * @return boolean
     */
    public static boolean setGraphicsMagickPath(String graphicsMagickPath) {
        if(StringUtils.isEmpty(graphicsMagickPath)) {
            return false;
        }
        File graphicsMagickFile = new File(graphicsMagickPath);
        if(!graphicsMagickFile.exists()) {
            logger.error("设置 gm 执行路径失败！gm 可执行文件不存在");
            return false;
        }
        graphicsMagickFile.setExecutable(true);
        MediaConverter.graphicsMagickPath = graphicsMagickPath;
        return true;
    }

    /**
     * 获取 ffmpeg 路径
     * @return String
     */
    public static String getFfmpegPath() {
        return ffmpegPath;
    }

    /**
     * 设置 ffmpeg 路径
     * @param ffmpegPath ffmpeg
     * @return boolean
     */
    public static boolean setFfmpegPath(String ffmpegPath) {
        if(StringUtils.isEmpty(ffmpegPath)) {
            return false;
        }
        File ffmpegFile = new File(ffmpegPath);
        if(!ffmpegFile.exists()) {
            logger.error("设置 ffmpeg 执行路径失败！ffmpeg 可执行文件不存在");
            return false;
        }
        ffmpegFile.setExecutable(true);
        MediaConverter.ffmpegPath = ffmpegPath;
        return true;
    }

    /**
     * 设置 ffprobe 路径
     * @param ffprobePath ffprobePath
     * @return boolean
     */
    public static boolean setFfprobePath(String ffprobePath) {
        if(StringUtils.isEmpty(ffprobePath)) {
            return false;
        }
        File ffprobeFile = new File(ffprobePath);
        if(!ffprobeFile.exists()) {
            logger.error("设置 ffprobe 执行路径失败！ffprobe 可执行文件不存在");
            return false;
        }
        ffprobeFile.setExecutable(true);
        MediaConverter.ffprobePath = ffprobePath;
        return true;
    }

    /**
     * 获取 ffprobePath 执行路径
     * @return String
     */
    public static String getFfprobePath() {
        return ffprobePath;
    }

    /**
     * ffmpeg 是否可以执行
     * @return boolean
     */
    public static boolean ffmpegCanExecute() {
        File ffmpegFile = new File(getFfmpegPath());
        if(!ffmpegFile.exists()) {
            logger.error("ffmpeg 工作状态异常! ffmpeg 路径不存在~~");
            return false;
        }
        ffmpegFile.setExecutable(true);
        List<String> cmds = new ArrayList<>(1);
        cmds.add("-version");
        String versionStr = executeCommand(cmds, getFfmpegPath());
        return !StringUtils.isEmpty(versionStr);
    }

    /**
     * gm 是否可以执行
     * @return boolean
     */
    public static boolean gmCanExecute() {
        File gmFile = new File(getGraphicsMagickPath());
        if(!gmFile.exists()) {
            logger.error("GraphicsMagick 工作状态异常! graphicsMagickPath 路径不存在~~");
            return false;
        }
        if(!gmFile.canExecute()) {
            gmFile.setExecutable(true);
        }
        List<String> cmds = new ArrayList<>(1);
        cmds.add("-version");
        String versionStr = executeCommand(cmds, getGraphicsMagickPath());
        return !StringUtils.isEmpty(versionStr);
    }

    /**
     * ffprobe 是否可以执行
     * @return boolean
     */
    public static boolean ffprobeCanExecute() {
        File ffprobeFile = new File(getFfprobePath());
        if(!ffprobeFile.exists()) {
            logger.error("ffprobe 工作状态异常! ffprobe 路径不存在~~");
            return false;
        }
        if(!ffprobeFile.canExecute()) {
            ffprobeFile.setExecutable(true);
        }
        List<String> cmds = new ArrayList<>(1);
        cmds.add("-version");
        String versionStr = executeCommand(cmds, getFfprobePath());
        return !StringUtils.isEmpty(versionStr);
    }

    /**
     * 视频转换
     * @param fileInput fileInput
     * @param fileOutput fileOutput
     * @param withAudio 是否携带音频，true，携带
     * @param crf crf 视频的质量，值越小视频质量越高（取值为 0-51，直接影响视频码率大小），取值可参考 CrfValueEnum.code
     * @param preset preset 指定视频的编码速率（速率越快压缩率越低），取值参考 PresetValueEnum.presetValue
     * @param width 视频宽度，为空则表示原视频宽度
     * @param height 视频高度，为空则表示原视频高度
     */
    public static void convertVideo(File fileInput, File fileOutput, boolean withAudio, Integer crf, String preset, Integer width, Integer height) {
        if(fileInput == null || !fileInput.exists()) {
            throw new RuntimeException("源文件不存在");
        }
        if(null == fileOutput) {
            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
        }
        if(!fileOutput.exists()) {
            try{
                fileOutput.createNewFile();
            } catch (IOException ex){
                System.out.println("视频转换创建文件失败");
            }
        }

        String format = FileUtil.getFileExtension(fileInput.toPath());
        if(!isIllegalFormat(format, new String[]{"avi", "mp4", "rmvb"})){
            throw new RuntimeException("无法解析视频格式");
        }
        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(fileInput.getAbsolutePath());
        //是否保留音频
        if(!withAudio) {
            //去掉音频
            command.add("-an");
        }
        if(null != width && width > 0 && null != height && height > 0) {
            command.add("-s");
            String resolution = String.format("%sx%s", width.toString(), height.toString());
            command.add(resolution);
        }
        // 指定输出视频文件时使用的编码器
        command.add("-vcodec");
        // 指定使用 x264 编码
        command.add("libx264");
        // 当使用 x264 的时候需要带上该餐胡
        command.add("-preset");
        command.add(preset);
        // crf 值。值越小，视频质量越高
        command.add("-crf");
        command.add(crf.toString());

        // 当存在已输出文件时，不提示是否覆盖
        command.add("-y");
        command.add(fileOutput.getAbsolutePath());

        executeCommand(command, getFfmpegPath());
    }


    /**
     * 给视频添加文字水印
     * 完整命令  ffmpeg -y -i video.mp4 -vf drawtext=fontfile=arial.ttf:text=bilibili:x=w-tw-10:y=10:fontsize=60:fontcolor=gray output.mp4
     *
     * @param videoFile  输入文件
     * @param outputFile 输出文件
     * @param text 水印文字
     */
    public static void addWaterMarkForVideo(File videoFile, File outputFile, String text) {
        if(videoFile == null || !videoFile.exists()) {
            throw new RuntimeException("原视频文件不存在");
        }

        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(videoFile.getAbsolutePath());
        command.add("-y");
        command.add("-vf");

        StringBuilder paramsBuilder = new StringBuilder("drawtext=");
        paramsBuilder.append("fontfile='C\\:\\\\Windows\\\\Fonts\\\\STHUPO.TTF'").append(":text=").append(text)
                .append(":x=w-tw-16")
                .append(":y=16")
                .append(":fontsize=60")
                .append(":fontcolor=gray");
        command.add(paramsBuilder.toString());
//        command.add("drawtext=fontfile=arial.ttf");
//        command.add(":text=");
//        command.add(text);
//        command.add(":x=w-tw-16");
//        command.add(":y=16");
//        command.add(":fontsie=60");
//        command.add("fontcolor=gray");
        command.add(outputFile.getAbsolutePath());
        String executeResult = executeCommand(command, getFfmpegPath());
        System.out.println("添加水印:" + executeResult);
    }


    /**
     * 抽帧
     * @param videoFile 原视频
     * @param path 转换后的文件路径
     * @param time 开始截取视频帧的时间点，单位 s
     * @param width 截图的视频帧的图片的宽度 单位 px
     * @param height 截图的视频图片的高度 单位 px，需要大于 20
     * @param timeLength 截取的视频帧的时长（从开始时间算，单位 s，需要小于原视频的最大时长）
     * @param isContinue false - 静态图（只截取 time 时间点的那一帧图片）true，动态图，（截取时间从 time 时间开始，timeLength 这段时间内的多张图）
     */
    public static void cutVideoFrame(File videoFile, String path, Time time, int width, int height, int timeLength, boolean isContinue) {
        if(videoFile == null || !videoFile.exists()) {
            throw new RuntimeException("原视频文件不存在");
        }
        if(StringUtils.isEmpty(path)) {
            throw new RuntimeException("转换后的文件路径为空，请检查转换后的文件存放路径是否正确");
        }
        VideoMetaInfo videoMetaInfo = getVideoMetaInfo(videoFile);

        if (null == videoMetaInfo) {
            throw new RuntimeException("未解析到视频信息");
        }
        if (time.getTime() + timeLength > videoMetaInfo.getDuration()) {
            throw new RuntimeException("开始截取视频帧的时间点不合法：" + time.toString() + "，因为截取时间点晚于视频的最后时间点");
        }

        if (width <= 20 || height <= 20) {
            throw new RuntimeException("截取的视频帧图片的宽度或高度不合法，宽高值必须大于20");
        }

        try {
            List<String> command = new ArrayList<>();
            command.add("-ss");
            command.add(time.toString());
            if(isContinue) {
                command.add("-t");
                command.add(timeLength+"");
            } else {
                command.add("-vframes");
                command.add("1");
            }
            command.add("-i");
            command.add(videoFile.getAbsolutePath());
            command.add("-an");
            command.add("-f");
            command.add("image2");
            if(isContinue) {
                command.add("-r");
                command.add("3");
            }
            command.add("-s");
            command.add(width + "*" + height);
            if(!isContinue) {
                command.add(path + File.separator + "foo-%3d.jpeg");
            } else {
                command.add(path);
            }
            executeCommand(command, getFfmpegPath());
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("视频帧抽取过程出错！[{}]", ex.getMessage(), ex);
        }

    }



    public static String getMetaInfo(File inputFile) {
        if(inputFile == null || !inputFile.exists()) {
            return null;
        }
        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(inputFile.getAbsolutePath());
        return executeCommand(command, getFfmpegPath());
    }


    private static boolean isIllegalFormat(String format, String[] formats) {
        if(formats == null || formats.length == 0) {
            return false;
        }
        if(StringUtils.isEmpty(format)) {
            return false;
        }

        return Arrays.stream(formats).anyMatch(e -> StringUtils.endsWithIgnoreCase(e,format));
    }



    public static String executeCommand(List<String> commands, String bash) {
        if(CollectionUtils.isEmpty(commands)) {
            return null;
        }
        LinkedList<String> cmds = new LinkedList<>(commands);
        cmds.addFirst(bash);


        String commandStr = String.join(" ", cmds);
        logger.info("进程执行的指令为：[{}]", commandStr);

        final Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {

            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmds);

            process = builder.start();

//            logger.info("进程执行指令开始: [{}]", builder.toString());

            // 取输出流和错误流信息
            PrintStream errorStream = new PrintStream(process.getErrorStream());
            PrintStream inputStream = new PrintStream(process.getInputStream());
            errorStream.start();
            inputStream.start();

            // 等待进程执行结束
            process.waitFor();

            //获取执行结果
            String result = errorStream.stringBuffer.append(inputStream.stringBuffer).toString();

            // 输出执行的命令信息
            String cmdStr = Arrays.toString(cmds.toArray()).replace(",", "");
            String resultStr = StringUtils.isEmpty(result) ? "【异常】" : "正常";

            return result;
        } catch (Exception ex){
            logger.error("进程执行指令异常:[{}]", ex.getMessage(), ex);
            return null;
        } finally {
            if(null != process) {
                ProcessKiller processKiller = new ProcessKiller(process);
                // JVM退出时，先通过钩子关闭进程
                runtime.addShutdownHook(processKiller);
            }
        }
    }


    public static VideoMetaInfo getVideoMetaInfo(File videoFile) {
        if(videoFile == null || !videoFile.exists()) {
            return null;
        }
        String videoMetaInfoStr = getVideoMetaInfoByFfmpeg(videoFile);

        System.out.println("视频信息 " + videoMetaInfoStr);

        Matcher matcher = DURATION_PATTERN.matcher(videoMetaInfoStr);
        Matcher videoStreamMatcher = VIDEO_STREAM_PATTERN.matcher(videoMetaInfoStr);

        // 视频持续时长
        long duration = 0L;
        //视频码率
        int videoBitRate;
        //视频大小
        Long videoSize = videoFile.length();
        //视频格式
        String format = StringUtils.getFilename(videoFile.getAbsolutePath());

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
            System.out.println(hours);
            System.out.println(minutes);
            System.out.println(seconds);
            System.out.println(dec);
            System.out.println(videoBitRate);
        }

        if(videoStreamMatcher.find()) {
            videoEncoder = videoStreamMatcher.group(1);
            String s2 = videoStreamMatcher.group(2);
            videoWidth = Integer.parseInt(videoStreamMatcher.group(3));
            videoHeight = Integer.parseInt(videoStreamMatcher.group(4));
            String s5 = videoStreamMatcher.group(5);
            videoFramerate = Float.parseFloat(videoStreamMatcher.group(6));
            System.out.println(videoEncoder);
            System.out.println(s2);
            System.out.println(videoWidth);
            System.out.println(videoHeight);
            System.out.println(s5);
            System.out.println(videoFramerate);
        }

        VideoMetaInfo videoMetaInfo = new VideoMetaInfo(videoWidth, videoHeight,
                Math.toIntExact(duration), format);
//        videoMetaInfo.
        return videoMetaInfo;
    }


    private static String getVideoMetaInfoByFfmpeg(File file) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(file.getAbsolutePath());
        return executeCommand(commands, getFfmpegPath());
    }

    /**
     * 裁剪图片
     * @param inputFile inputFile
     * @param quality quality
     * @return String
     */
    public static String clipImage(File inputFile, File outputFile, String quality) {
        if(inputFile == null || !inputFile.exists()) {
            throw new RuntimeException("原文件为空");
        }
        List<String> commands = new ArrayList<>();
        commands.add("convert");
        commands.add(inputFile.getAbsolutePath());
        if(!StringUtils.isEmpty(quality)) {
            commands.add("-resize");
            commands.add(quality + "%");
        }
        Path parent = inputFile.toPath().getParent();
        String name = inputFile.getName();
//        File  = Paths.get().toFile();
        commands.add(outputFile.getAbsolutePath());
        return executeCommand(commands, getGraphicsMagickPath());
    }

    /**
     * 从文件中获取基本信息
     * gm identify -format '%wx%h' C:/Users/admin/Desktop/xx.jpg
     * => 200x150
     * @param imageFile imageFile
     * @return ImageMetaInfo
     */
    private static ImageMetaInfo getImageInfoFromFile(File imageFile) {
        BufferedImage image = null;
        try{
            if(imageFile == null || !imageFile.exists()) {
                return null;
            }
            image = ImageIO.read(imageFile);
            int width = image.getWidth();
            int height = image.getHeight();
            long size = imageFile.length();
            String fileExtension = FileUtil.getFileExtension(imageFile.toPath());
            return new ImageMetaInfo(width,height, fileExtension);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取图片基本信息
     * @param inputFile inputFile
     * @return ImageMetaInfo
     */
    private static ImageMetaInfo getImageMetaInfoFromGm(File inputFile) {
        if(inputFile == null || !inputFile.exists()) {
            return null;
        }
        List<String> commands = new ArrayList<>();
        commands.add("identify");
        commands.add("-format");
        commands.add("%wx%h");
        commands.add(inputFile.getAbsolutePath());

        String result = executeCommand(commands, getGraphicsMagickPath());
        String ext = FileUtil.getFileExtension(inputFile.toPath());
        if(StringUtils.isEmpty(result)) {
            return null;
        }
        String[] xes = result.split("x");
        return new ImageMetaInfo(Integer.parseInt(xes[0]), Integer.parseInt(xes[1]), ext);
    }

    /**
     * 为图片添加水印
     * gm  convert image_2000x3000.jpg -fill white -pointsize 128 -font "C:/Windows/Fonts/STCAIYUN.TTF" -gravity northeast -draw "text 40,120 'bilibili'" dest-c.jpg
     */
    private static void addWaterMarkForImage(File inputFile, File outputFile, String text) {
        if(inputFile == null || !inputFile.exists()) {
            logger.info("源文件不存在");
            return;
        }
        if(StringUtils.isEmpty(text)) {
            logger.info("水印不存在");
            return;
        }

        List<String> commands = new ArrayList<>();
        commands.add("convert");
        commands.add(inputFile.getAbsolutePath());
        commands.add("-fill white");
        commands.add("-pointsize 128");
        commands.add("-font C:/Windows/Fonts/STCAIYUN.TTF");
        commands.add("-gravity northeast");
        commands.add("-draw");
        commands.add("text 40,120");
        commands.add("'");
        commands.add(text.toLowerCase());
        commands.add("'");
        commands.add(outputFile.getAbsolutePath());

        executeCommand(commands, getGraphicsMagickPath());

    }

    private static class ProcessKiller extends Thread {
        private final Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();
            logger.error("销毁进程：[{}]", process.toString());
        }
    }

    static class PrintStream extends Thread {
        InputStream inputStream;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                if(null == inputStream) {
                    logger.error("输入流出错，输出流为空");
                    return;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("读取输入流出错:[{}]", ex.getMessage(), ex);
            } finally {
                try {
                    if(null != bufferedReader) {
                        bufferedReader.close();
                    }
                    if(null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("关闭流失败:[{}]", e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] rags) {

        MediaConverter.setFfmpegPath("C:\\converter\\ffmpeg\\ffmpeg.exe");
        MediaConverter.setGraphicsMagickPath("C:\\converter\\GraphicsMagick\\gm.exe");
//        boolean executable = MediaConverter.isExecutable();
//        System.out.println(executable);

//        VideoMetaInfo videoMetaInfo = MediaConverter.getVideoMetaInfo(new File("C:\\Users\\admin\\Desktop\\video-from-phone.mp4"));
//        System.out.println(videoMetaInfo);
        long start = System.currentTimeMillis();
        long second = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(second);
        System.out.println(start);
//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                long executeTime = System.currentTimeMillis();
//                System.out.println(executeTime-start);
//            }
//        }, 3, TimeUnit.SECONDS);
//        while (true) {
//
//        }

        //添加水印
//        MediaConverter.addWaterMark(new File("C:\\Users\\admin\\Desktop\\video-from-phone.mp4"),
//                new File("C:\\Users\\admin\\Desktop\\video-from-phone-watermarkwww.mp4"),
//                "bilibili".toLowerCase());

        //获取图片基本信息
//        ImageMetaInfo imageMetaInfo = MediaConverter.getImageMetaInfoFromGm(new File("C:\\Users\\admin\\Desktop\\image_jpeg__RC-2021-04-14_7243_1618384294446.jpg"));
//        System.out.println(imageMetaInfo.getHeight());
//        System.out.println(imageMetaInfo.getWidth());
//        System.out.println(imageMetaInfo.getExtension());

        //图片放大缩小
        File inputFile = new File("C:\\Users\\admin\\Desktop\\image_jpeg__RC-2021-04-14_7243_1618384294446.jpg");
        File outputFile = new File("C:\\Users\\admin\\Desktop\\image_xx.jpg");
//        MediaConverter.clipImage(inputFile, outputFile,"50");
        MediaConverter.addWaterMarkForImage(inputFile, outputFile, "qwli7.com");
        while (true){
            //死循环
        }

    }

    /**
     * 获取两个时间的之间的秒数
     * @param start start
     * @param end end
     * @return long
     */
    public static long getSeconds(LocalDateTime start, LocalDateTime end) {
       if(start == null || end == null || end.isBefore(start)) {
           return -1;
       }
       long startMills = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
       long endMills = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
       long millsInterval = endMills - startMills;
       return millsInterval/1000;
    }

    static class Resize implements Serializable {

        private int height;

        private int width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
