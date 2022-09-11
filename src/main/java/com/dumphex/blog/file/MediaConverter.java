package com.dumphex.blog.file;

import com.dumphex.blog.file.vo.VideoConvertParams;
import com.dumphex.blog.file.vo.VideoCutParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    private static final List<String> VIDEO_FORMAT = Arrays.asList("mp4", "rmvb", "rm", "flv");
    private static final List<String> IMAGE_FORMAT = Arrays.asList("jpeg", "jpg", "png", "webp");
    private static final List<String> TEXT_FORMAT = Arrays.asList("pdf", "doc", "pptx", "ppt", "txt", "md", "html", "css", "js", "xml", "json", "properties", "config");

    /**
     * 默认预设 crf 值
     * @see VideoConvertParams
     */
    private final static Integer DEFAULT_CRF = 18;

    /**
     * 默认预设属性值
     * @see VideoConvertParams
     */
    private final static String DEFAULT_PRESET = "veryslow";

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
     * @param videoConvertParams convertParam 转换参数
     */
    public static void convertVideo(VideoConvertParams videoConvertParams) {
        final File inputFile = videoConvertParams.getInputFile();
        if(inputFile == null || !inputFile.exists()) {
            throw new RuntimeException("源文件不存在");
        }
//        if(null == outputFile) {
//            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
//        }
//        if(!outputFile.exists()) {
//            try{
//                outputFile.createNewFile();
//            } catch (IOException ex){
//                System.out.println("视频转换创建文件失败");
//            }
//        }

        String format = StringUtils.getFilenameExtension(inputFile.getName());
        if(!isIllegalFormat(format, new String[]{"avi", "mp4", "rmvb"})){
            throw new RuntimeException("无法解析视频格式");
        }
        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(inputFile.getAbsolutePath());

        final boolean withAudio = videoConvertParams.isWithAudio();

        //是否保留音频
        if(!withAudio) {
            //去掉音频
            command.add("-an");
        }

        final Integer width = videoConvertParams.getWidth();
        final Integer height = videoConvertParams.getHeight();

        if(null != width && width > 0 && null != height && height > 0) {
            command.add("-s");
            String resolution = String.format("%sx%s", width.toString(), height.toString());
            command.add(resolution);
        }
        // 指定输出视频文件时使用的编码器
        command.add("-vcodec");
        // 指定使用 x264 编码
        command.add("libx264");
        // 当使用 x264 的时候需要带带上参数
        command.add("-preset");
        final String preset = videoConvertParams.getPreset();
        if(videoConvertParams.matchPreset(preset)) {
            command.add(preset);
        } else {
            command.add(DEFAULT_PRESET);
        }
        // crf 值。值越小，视频质量越高
        command.add("-crf");

        Integer crf = videoConvertParams.getCrf();
        if(crf == null || 0 > crf || crf > 51) {
            crf = DEFAULT_CRF;
        }
        command.add(crf.toString());

        // 当存在已输出文件时，不提示是否覆盖
        command.add("-y");

        // 转换文件规则
        final Path path = inputFile.toPath();
        final Path parent = path.getParent();
//        Path outputPath = Paths.get(parent.toString(), "" + FileUtil.getFileExtension(path));

        command.add("/Users/liqiwen/Desktop/video.mp4");

        executeCommand(command, getFfmpegPath());
    }


    /**
     * 抽帧
     * @param cutParams cutParams
     */
    public static void cutVideoFrame(VideoCutParams cutParams) {
        final File videoFile = cutParams.getInputFile();
        if(videoFile == null || !videoFile.exists()) {
            throw new RuntimeException("原视频文件不存在");
        }
        final String path = cutParams.getPath();
        if(StringUtils.isEmpty(path)) {
            throw new RuntimeException("转换后的文件路径为空，请检查转换后的文件存放路径是否正确");
        }
//        VideoMetaInfo videoMetaInfo = getVideoMetaInfo(videoFile);
        VideoMetaInfo videoMetaInfo = null;

        if (null == videoMetaInfo) {
            throw new RuntimeException("未解析到视频信息");
        }
        //开始时间
        final LocalTime localTime = cutParams.getLocalTime();
        final int timeLength = cutParams.getTimeLength();
//        if (time.getTime() + timeLength > videoMetaInfo.getDuration()) {
//            throw new RuntimeException("开始截取视频帧的时间点不合法：" + time.toString() + "，因为截取时间点晚于视频的最后时间点");
//        }

        Integer height = cutParams.getHeight();
        Integer width = cutParams.getWidth();
        if (width == null || width <= 20 || height == null || height <= 20) {
            width = videoMetaInfo.getWidth();
            height = videoMetaInfo.getHeight();
        }
        if(width > videoMetaInfo.getWidth()) {
            width = videoMetaInfo.getWidth();
        }
        if(height > videoMetaInfo.getHeight()) {
            height = videoMetaInfo.getHeight();
        }

        try {
            List<String> command = new ArrayList<>();
            command.add("-ss");
//            command.add(time.toString()); //从什么时候开始截图
            command.add("00:00:00");
            command.add("-i");
            command.add(videoFile.getAbsolutePath());

            if(cutParams.isContinuous()) {
                command.add("-t");
//                ffmpeg -ss 0:1:30 -t 0:0:20 -i input.avi -vcodec copy -acodec copy output.avi
                command.add(timeLength+""); //截取视频时长，该截图需要持续多长时间
            } else {
                command.add("-vframes");
                command.add("1");
            }
            //去除音频编码
            command.add("-an");

//            if(cutParams.isContinuous()) {
//                command.add("-f");
//                command.add("image2");
//            }
            if(cutParams.isContinuous()) {
                // -r 提取视频的频率，多长时间提取一次
                command.add("-r");
                command.add("3");
            }
            command.add("-s");
            command.add(width + "*" + height);

            //重名文件直接保存
            command.add("-y");

            if(cutParams.isContinuous()) {
                command.add(path + File.separator + "-%3d.jpeg");
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


    /**
     * 调用进程执行
     * @param commands 待执行的命令
     * @param bash bash 或者 exe 所在的目录
     * @return String
     */
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


//    public static VideoMetaInfo getVideoMetaInfo(File videoFile) {
//        if(videoFile == null || !videoFile.exists()) {
//            return null;
//        }
//        String videoMetaInfoStr = getVideoMetaInfoByFfmpeg(videoFile);
//
//        System.out.println("视频信息 " + videoMetaInfoStr);
//
//        Matcher matcher = DURATION_PATTERN.matcher(videoMetaInfoStr);
//        Matcher videoStreamMatcher = VIDEO_STREAM_PATTERN.matcher(videoMetaInfoStr);
//
//        // 视频持续时长
//        long duration = 0L;
//        //视频码率
//        int videoBitRate;
//        //视频大小
//        Long videoSize = videoFile.length();
//        //视频格式
//        String format = StringUtils.getFilename(videoFile.getAbsolutePath());
//
//        //视频编码器
//        String videoEncoder;
//        Integer videoHeight = 0;
//        int videoWidth = 0;
//        //视频帧率
//        Float videoFramerate;
//
//        //音频格式
//        String musicFormat = "";
//        // 音频采样率
//        long sampleRate = 0L;
//        //音频码率
//        Integer musicBitRate = 0;
//
//        if(matcher.find()) {
//            //小时
//            long hours = Integer.parseInt(matcher.group(1));
//            //分钟
//            long minutes = Integer.parseInt(matcher.group(2));
//            //秒数
//            long seconds = Integer.parseInt(matcher.group(3));
//            long dec = Integer.parseInt(matcher.group(4));
//            duration = dec * 100L + seconds * 1000L + minutes * 60L * 1000L + hours * 60L * 60L * 1000L;
//
//            videoBitRate = Integer.parseInt(matcher.group(6));
//            System.out.println(hours);
//            System.out.println(minutes);
//            System.out.println(seconds);
//            System.out.println(dec);
//            System.out.println(videoBitRate);
//        }
//
//        if(videoStreamMatcher.find()) {
//            videoEncoder = videoStreamMatcher.group(1);
//            String s2 = videoStreamMatcher.group(2);
//            videoWidth = Integer.parseInt(videoStreamMatcher.group(3));
//            videoHeight = Integer.parseInt(videoStreamMatcher.group(4));
//            String s5 = videoStreamMatcher.group(5);
//            videoFramerate = Float.parseFloat(videoStreamMatcher.group(6));
//            System.out.println(videoEncoder);
//            System.out.println(s2);
//            System.out.println(videoWidth);
//            System.out.println(videoHeight);
//            System.out.println(s5);
//            System.out.println(videoFramerate);
//        }
//
//        VideoMetaInfo videoMetaInfo = new VideoMetaInfo(videoWidth, videoHeight,
//                Math.toIntExact(duration), format);
//        videoMetaInfo.setSize(videoSize);
//        return videoMetaInfo;
//    }

    /**
     * 通过 ffmpeg 获取视频信息
     * 完整命令：ffmpeg -i fileInput
     * 通过正则表达式获取到视频信息
     * @param file file or url
     * @return String
     */
    private static String getVideoMetaInfoByFfmpeg(File file) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(file.getAbsolutePath());
        return executeCommand(commands, getFfmpegPath());
    }

    /**
     * 完整命令：ffprobe -v quiet -print_format json -show_format -show_streams /Users/liqiwen/Desktop/video.mp4
     * 通过 ffprobe 获取到视频信息
     * @param file file
     * @return String
     */
    private static String getVideoMetaInfoByFfprobe(File file) {
        List<String> commands = new ArrayList<>();
        commands.add("-v");
        commands.add("quiet");
        commands.add("-print_format");
        // 输出格式为 json
        commands.add("json");
        // 显示格式
        commands.add("-show_format");
        //显示流信息
        commands.add("-show_streams");
        commands.add(file.getAbsolutePath());
        // 返回的是 json 类型的数据，获取到数据进行解析即可
        return executeCommand(commands, getFfprobePath());
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
            return new ImageMetaInfo(width,height, size, fileExtension);
        } catch (Exception ex){
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
        return new ImageMetaInfo(Integer.parseInt(xes[0]), Integer.parseInt(xes[1]), 0, ext);
    }



    public static boolean isVideo(Path dest) {
        if(dest == null) {
            return false;
        }
        final File file = dest.toFile();
        if(file.isDirectory() || !file.exists()) {
            return false;
        }
        final Path fileName = dest.getFileName();
        final String ext = StringUtils.getFilenameExtension(fileName.toString());
        if(StringUtils.isEmpty(ext)) {
            return false;
        }
        return VIDEO_FORMAT.contains(ext.toLowerCase());
    }

    public static boolean canHandle(Path dest) {
        return true;
    }

    public static boolean isText(String ext) {
        if(StringUtils.isEmpty(ext)) {
            return false;
        }
        return TEXT_FORMAT.contains(ext);
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
        File inputFile = new File("C:\\Users\\admin\\Desktop\\image_jpeg__RC-2021-04-14_7243_1618384294446.jpg");
        File outputFile = new File("C:\\Users\\admin\\Desktop\\image_xx.jpg");

        //windows
//        MediaConverter.setFfmpegPath("C:\\converter\\ffmpeg\\ffmpeg.exe");
//        MediaConverter.setGraphicsMagickPath("C:\\converter\\GraphicsMagick\\gm.exe");


//        boolean executable = MediaConverter.isExecutable();
//        System.out.println(executable);

//        VideoMetaInfo videoMetaInfo = MediaConverter.getVideoMetaInfo(new File("C:\\Users\\admin\\Desktop\\video-from-phone.mp4"));
//        System.out.println(videoMetaInfo);


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
//        MediaConverter.clipImage(inputFile, outputFile,"50");
//        MediaConverter.addWaterMarkForImage(inputFile, outputFile, "qwli7.com");

        //视频转换
//        VideoConvertParams videoConvertParams = new VideoConvertParams();
//        videoConvertParams.setInputFile(new File("/Users/liqiwen/Desktop/obj_w5zDkcKQw6LDiWzDgcK2_1374635483_a9ac_58c9_db6c_f0730e472dc0176e37dbcef3e0098b76.mp4"));
//        videoConvertParams.setWithAudio(true);
//        videoConvertParams.setCrf(28);
//        MediaConverter.convertVideo(videoConvertParams);

        VideoCutParams cutParams = new VideoCutParams();
        cutParams.setInputFile(new File("/Users/liqiwen/Desktop/video.mp4"));
        cutParams.setPath(Paths.get("/Users/liqiwen/Desktop/", "video_thumb.png").toString());
        cutParams.setContinuous(false);
        cutParams.setHeight(450);
        cutParams.setWidth(600);
        MediaConverter.cutVideoFrame(cutParams);
        while (true){
            //死循环
        }

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
