package com.qwli7.blog.file.converter;

import com.qwli7.blog.file.MediaConverter;
import com.qwli7.blog.file.vo.ControlArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 抽象媒体转换器
 * @author liqiwen
 * @since 2.2
 */
public abstract class AbstractMediaConverter {
    /**
     * 版本信息
     */
    public static final String VERSION = "-version";

    /**
     * 强制覆盖保存
     */
    public static final String FORCE_SAVE = "-y";

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
    protected void convert(File sourceFile, File targetFile, ControlArgs controlArgs) {
        if(!sourceFile.exists()) {
            logger.error("method<convert> 源文件存在");
            return;
        }
        final String action = controlArgs.getAction();
        if(StringUtils.isEmpty(action)) {
            logger.error("method<convert> 无指定的转换动作");
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
        List<String> cmds = new ArrayList<>(1);
        cmds.add(VERSION);
        String versionStr = doProcess(cmds, getFfmpegPath());
        return !StringUtils.isEmpty(versionStr);
    }

    /**
     *
     * @return
     */
    public String getFfprobePath() {
        return ffprobePath;
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
        List<String> cmds = new ArrayList<>(1);
        cmds.add(VERSION);
        String versionStr = doProcess(cmds, getGraphicsMagickPath());
        return !StringUtils.isEmpty(versionStr);
    }

    public String getGraphicsMagickPath() {
        return graphicsMagickPath;
    }

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    /**
     * 调用进程执行
     * @param commands 待执行的命令
     * @param bash bash 或者 exe 所在的目录
     * @return String
     */
    public String doProcess(List<String> commands, String bash) {
        if(CollectionUtils.isEmpty(commands)) {
            return null;
        }
        LinkedList<String> cmds = new LinkedList<>(commands);
        cmds.addFirst(bash);

        String commandStr = String.join(" ", cmds);
        logger.info("method<doProcess> 进程执行的完整指令为：[{}]", commandStr);
        final Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmds);
            process = builder.start();
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
            logger.info("method<doProcess> 进行执行指令结果: [{}]", result);
            return result;
        } catch (Exception ex){
            logger.error("method<doProcess> 进程执行指令异常: [{}]", ex.getMessage(), ex);
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
            logger.error("method<ProcessKiller#run> 销毁进程：[{}]", process);
        }
    }

    /**
     * 打印结果的线程
     */
    public class PrintStream extends Thread {
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
                    logger.error("method<PrintStream#run> 输入流出错，输出流为空");
                    return;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
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
        }
    }
}
