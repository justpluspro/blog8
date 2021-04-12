package com.qwli7.blog.file;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Path;
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
 * 图片： 依赖 Graphack Image
 * 1. 缩放
 * 2. 裁剪
 * 3. 加水印
 */
public class MediaConverter implements Serializable {

    private static String ffmpegPath = "/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin/ffmpeg";
    private static String ffprobePath = "/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin/ffprobe";


    /**
     * 1. 从视频中获取缩略图
     * 2. 上传视频的缩略图
     * @return String
     */
    public static String getPreviewImageFromVideo(Path path) {


        final Runtime runtime = Runtime.getRuntime();
//        runtime.exec("")


        return "";
    }


    public static String getFfmpegPath() {
        return ffmpegPath;
    }

    public static boolean setFfmpegPath(String ffmpegPath) {
        if(StringUtils.isEmpty(ffmpegPath)) {
            return false;
        }
        File ffmpegFile = new File(ffmpegPath);
        if(!ffmpegFile.exists()) {
            System.out.println("设置执行路径失败");
            return false;
        }
        MediaConverter.ffmpegPath = ffmpegPath;
        return true;
    }

    public static boolean isExecutable() {
        File ffmpegFile = new File(getFfmpegPath());
        if(!ffmpegFile.exists()) {
            System.out.println("工作状态异常");
            return false;
        }
        List<String> cmds = new ArrayList<>(1);
        cmds.add("-version");
        String ffmpegVersionStr = executeCommand(cmds);
        if(StringUtils.isEmpty(ffmpegVersionStr)) {
            return false;
        }
        return true;
    }



    public static String getMetaInfo(File inputFile) {
        if(inputFile == null || !inputFile.exists()) {
            return null;
        }
        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(inputFile.getAbsolutePath());
        final String executeResult = executeCommand(command);
        return executeResult;
    }


    private static boolean isIllegalFormat(String format, String[] formats) {
        if(formats == null || formats.length == 0) {
            return false;
        }
        if(StringUtils.isEmpty(format)) {
            return false;
        }

        return Arrays.stream(formats).anyMatch(e-> StringUtils.endsWithIgnoreCase(e,format));

    }



    public static String executeCommand(List<String> commands) {
        if(CollectionUtils.isEmpty(commands)) {
            return null;
        }
        LinkedList<String> cmds = new LinkedList<>(commands);
        cmds.addFirst(getFfmpegPath());

        System.out.println("执行指令为：" + cmds);

            final Runtime runtime = Runtime.getRuntime();
            Process process = null;
        try {

            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmds);

            process = builder.start();
            System.out.println("指令执行开始:" + builder.toString());

            // 取输出流和错误流信息
            PrintStream errorStream = new PrintStream(process.getErrorStream());
            PrintStream inputStream = new PrintStream(process.getInputStream());

            process.waitFor();

            //获取执行结果
            String result = errorStream.toString();

            // 输出执行的命令信息
            String cmdStr = Arrays.toString(cmds.toArray()).replace(",", "");
            String resultStr = StringUtils.isEmpty(result) ? "【异常】" : "正常";

            return result;
        } catch (Exception ex){

        } finally {
            if(null != process) {
                ProcessKiller processKiller = new ProcessKiller(process);
                // JVM退出时，先通过钩子关闭进程
                runtime.addShutdownHook(processKiller);
            }
        }
        return null;
    }



    private static class ProcessKiller extends Thread {
        private final Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();
            System.out.println("销毁进程：" + process.toString());
        }
    }

    static class PrintStream extends Thread {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                if(null == inputStream) {
                    System.out.println("输入流出错，输出流为空");
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }catch (Exception ex) {
                System.out.println("读取输入流出错");
            } finally {
                try {
                    if(null != bufferedReader) {
                        bufferedReader.close();
                    }
                    if(null != inputStream) {
                        inputStream.close();
                    }
                }catch (IOException e) {
                    System.out.println("关闭流失败");
                }
            }
        }
    }

}
