package com.dumphex.blog.file.converter;

import com.dumphex.blog.util.StreamUtils;
import com.dumphex.blog.file.vo.ControlArgs;
import com.dumphex.blog.file.vo.VideoConvertParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 抽象媒体转换器
 * @author liqiwen
 * @since 2.2
 */
public abstract class AbstractMediaConverter {
    /**
     * 版本信息
     * -version
     */
    public static final String VERSION = "-version";

    /**
     * -i
     */
    public static final String I = "-i";

    /**
     * 强制覆盖保存
     * -y
     */
    public static final String FORCE_SAVE = "-y";

    /**
     * 预设 crf 值
     * crf
     */
    public static final Integer DEFAULT_CRF = 18;

    /**
     * 默认预设属性值（转换速度，越快质量越差）
     * @see VideoConvertParams
     */
    private final static String DEFAULT_PRESET = "veryslow";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * ffmpegPath 可执行路径
     */
    private final String ffmpegPath;

    /**
     * ffprobePath 可执行路径
     */
    private final String ffprobePath;

    /**
     * gmPath
     */
    private final String graphicsMagickPath;

    public AbstractMediaConverter(String ffmpegPath, String graphicsMagickPath) {
        this.ffmpegPath = ffmpegPath;
        this.graphicsMagickPath = graphicsMagickPath;
        this.ffprobePath = "";
    }

    /**
     * 转换方法
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param controlArgs 控制参数
     */
    public void convert(File sourceFile, File targetFile, ControlArgs controlArgs) {
        if(!sourceFile.exists()) {
            logger.error("method<convert> 源文件不存在");
            return;
        }
        final String action = controlArgs.getAction();

        final Optional<String> actionOp = Arrays.stream(ConvertAction.values()).map(ConvertAction::getAction).filter(e -> e.equals(action)).findAny();
        if(!actionOp.isPresent()) {
            logger.error("method<convert> 无指定的转换动作");
            return;
        }
        controlArgs.setInputFile(sourceFile);
        controlArgs.setOutputFile(targetFile);
        try {
            if (Files.notExists(targetFile.toPath().getParent())) {
                Files.createDirectories(targetFile.toPath().getParent());
            }
            if (Files.notExists(targetFile.toPath())) {
                Files.createFile(targetFile.toPath());
            }
        } catch (IOException ex){
            logger.error("method<convert> create targetFile error:[{}]", ex.getMessage(), ex);
            return;
        }
        //做一些校验工作
        doConvert(sourceFile, targetFile, controlArgs);
    }

    /**
     * ffmpeg 是否可以执行
     * @return boolean
     */
    public boolean ffmpegAvailable() {
        File ffmpegFile = new File(getFfmpegPath());
        if(!ffmpegFile.exists()) {
            logger.error("ffmpeg 工作状态异常! ffmpeg 路径不存在~~");
            return false;
        }
        if(!ffmpegFile.canExecute()) {
            if(!ffmpegFile.setExecutable(true)) {
                logger.error("method<ffmpegAvailable> 设置 ffmpeg 可执行权限失败");
                return false;
            }
        }
        List<String> cmds = new ArrayList<>(2);
        cmds.add(getFfmpegPath());
        cmds.add(VERSION);
        String versionStr = doProcess(cmds);
        return !StringUtils.isEmpty(versionStr);
    }


    /**
     * gm 是否可以执行
     * @return boolean
     */
    public boolean graphicsMagicAvailable() {
        File gmFile = new File(getGraphicsMagickPath());
        if(!gmFile.exists()) {
            logger.error("method<graphicsMagicAvailable> GraphicsMagick 工作状态异常! graphicsMagickPath 路径不存在~~");
            return false;
        }
        if(!gmFile.canExecute()) {
            if(!gmFile.setExecutable(true)) {
                logger.error("method<graphicsMagicAvailable> 设置 gm 可执行权限失败！");
                return false;
            }
        }
        List<String> cmds = new ArrayList<>(2);
        cmds.add(getGraphicsMagickPath());
        cmds.add(VERSION);
        String versionStr = doProcess(cmds);
        logger.info("检测 graphicsMagick 执行结果：" + versionStr);
        return !StringUtils.isEmpty(versionStr);
    }

    /**
     * 检查 ffprobe 工作状态是否正常
     * @return boolean
     */
    public boolean ffprobeAvailable() {
        File ffprobeFile = new File(getFfprobePath());
        if(Files.notExists(ffprobeFile.toPath())) {
            logger.error("method<ffprobeAvailable> ffprobe 工作状态异常! ffprobe 路径不存在");
            return false;
        }
        if(!ffprobeFile.canExecute()) {
            if(!ffprobeFile.setExecutable(true)) {
                logger.error("method<ffprobeAvailable> 设置 ffprobe 可执行权限失败! ");
                return false;
            }
        }
        List<String> cmds = new ArrayList<>(2);
        cmds.add(getFfprobePath());
        cmds.add(VERSION);
        String versionStr = doProcess(cmds);
        return !StringUtils.isEmpty(versionStr);
    }


    /**
     * 获取 ffprobe 可执行 path
     * @return String
     */
    public String getFfprobePath() {
        return ffprobePath;
    }

    /**
     * 获取 graphicsMagickPath 执行路径
     * @return String
     */
    public String getGraphicsMagickPath() {
        return graphicsMagickPath;
    }

    /**
     * 获取 ffmpegPath 执行路径
     * @return String
     */
    public String getFfmpegPath() {
        return ffmpegPath;
    }

    /**
     * 调用进程执行
     * @param commands 待执行的命令
     * @return String
     */
    public String doProcess(List<String> commands) {
        if(CollectionUtils.isEmpty(commands)) {
            return null;
        }

        String commandStr = String.join(" ", commands);
        logger.info("method<doProcess> full commands is：[{}]", commandStr);
        final Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            process = builder.start();
            // 取输出流和错误流信息
            final InputStream errorStream = process.getErrorStream();
            final InputStream inputStream = process.getInputStream();
            final OutputStream outputStream = process.getOutputStream();
            StringBuffer inputStreamBuffer = StreamUtils.readInputStream(inputStream);
            StringBuffer errorStreamBuffer = StreamUtils.readInputStream(errorStream);
            // 等待进程执行结束
            process.waitFor();
            //获取执行结果
            String result = errorStreamBuffer.append(inputStreamBuffer).toString();
            // 输出执行的命令信息
            logger.info("method<doProcess> command execute result is: [{}]", result);
            return result;
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("method<doProcess> command execute occurred exception: [{}]", ex.getMessage(), ex);
            return null;
        } finally {
            if(null != process) {
                ProcessKiller processKiller = new ProcessKiller(process);
                // JVM退出时，先通过钩子关闭进程
                runtime.addShutdownHook(processKiller);
            }
        }
    }


    /**
     * 不同类型的文件转换
     */
    public abstract void doConvert(File sourceFile, File targetFile, ControlArgs controlArgs);

    /**
     * 构建执行指令
     * @param controlArgs controlArgs
     * @return LinkedList
     */
    public abstract LinkedList<String> buildCommands(ControlArgs controlArgs);


    /**
     * 关闭进程的线程
     */
    public class ProcessKiller extends Thread {

        private final Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();
            logger.error("method<ProcessKiller#run> destroy process：[{}]", process);
        }
    }

    /**
     * 打印结果的线程
     */
    public class PrintStream implements Callable<String> {
        InputStream inputStream;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public String call() {
            try {
                if(null == inputStream) {
                    logger.error("method<PrintStream#run> 输入流出错，输出流为空");
                    return "-1";
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                logger.info("输入流/错误流执行结果: [{}]", stringBuffer.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("method<PrintStream#run> 读取输入流出错:[{}]", ex.getMessage(), ex);
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
                    logger.error("method<PrintStream#run> 关闭流失败:[{}]", e.getMessage(), e);
                }
            }
            return "1";
        }
    }
}
