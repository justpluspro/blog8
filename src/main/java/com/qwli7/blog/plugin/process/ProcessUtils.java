package com.qwli7.blog.plugin.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 进程工具执行类
 * @author qwli7
 * @since 1.0
 */
public class ProcessUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtils.class);
    public static int MAX_LINES = 100;

    public static ExecutorService executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());


    public static void handleInputStream(final BufferedReader reader, final AtomicBoolean isRunning, final List<String> lines) {
        executor.submit(() -> {
            try {
                String line;
                while (isRunning.get() && (line = reader.readLine()) != null) {
                    if (lines != null) {
                        if (lines.size() >= MAX_LINES) {
                            lines.remove(0);
                        }
                        lines.add(line);
                    }
                    logger.info(line);
                }
            } catch (IOException e) {
                logger.error("Process inputStream failed. ", e);
            }
        });
    }

    public static Integer waitExit(Process process, long timeout) throws InterruptedException {
        if (timeout == 0) {
            return process.waitFor();
        }

        Worker worker = new Worker(process);
        worker.start();
        worker.join(timeout);
        if (worker.exitCode != null) {
            // 返回进程执行结果状态码
            return worker.exitCode;
        } else {
            // 执行超时, 抛出异常
            throw new RuntimeException();
        }
    }

    /**
     * 执行 cmd 命令
     * @param cmd cmd
     * @param timeout timeout
     * @return ProcessResult
     * @throws IOException IOException
     */
    public static ProcessResult executeCmd(List<String> cmd, long timeout) throws IOException {
        return executeCmd(cmd, timeout, null);
    }

    /**
     * 执行 cmd 命令
     * @param cmd cmd
     * @param timeout timeout
     * @param workDirectory workDirectory
     * @return ProcessResult
     * @throws IOException IOException
     */
    public static ProcessResult executeCmdByRuntime(String cmd, long timeout, String workDirectory)throws IOException {
        Process process = null;

        BufferedReader stdout = null;
        AtomicBoolean isRunning = new AtomicBoolean(false);
        try {
            //1. 启动进程
            logger.info("execute cmd [{}]", cmd);
            process = Runtime.getRuntime().exec(cmd);
            // 2. 处理进程的stdout和stderr
            stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            List<String> lines = new LinkedList<>();
            handleInputStream(stdout, isRunning, lines);

            // 3. 阻塞等待进程返回
            logger.info("waiting for executing.....");
            try {
                int exitCode = waitExit(process, timeout);
                logger.info("exit code. [{}]", exitCode);
                if (exitCode != 0) {
                    String[] temp = cmd.split(" ");
                    throw new RuntimeException(Arrays.toString(temp));
                }

                return new ProcessResult().exitCode(exitCode).stdout(lines);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        } finally {
            if (process != null) {
                isRunning.set(false);
                //4. 销毁进程资源
                process.destroy();
                if(stdout != null) {
                    stdout.close();
                    logger.info(cmd, "[{}] has been exited");
                }
            }
        }
    }

    public static ProcessResult executeCmd(List<String> cmd, long timeout, String workDirectory) throws IOException {
        Process process = null;

        BufferedReader stdout = null;
        AtomicBoolean isRunning = new AtomicBoolean(false);
        try {
            //1. 启动进程
            logger.info("execute cmd {}", StringUtils.arrayToDelimitedString(cmd.toArray(), " "));
            String[] processCmd = cmd.toArray(new String[cmd.size()]);
            ProcessBuilder processBuilder = new ProcessBuilder(processCmd).redirectErrorStream(true);
            if (StringUtils.isEmpty(workDirectory)) {
                logger.info("work directory: " + workDirectory);
                processBuilder.directory(new File(workDirectory));
            }
            process = processBuilder.start();
            isRunning.set(true);
            // 2. 处理进程的stdout和stderr
            stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            List<String> lines = new LinkedList<>();
            handleInputStream(stdout, isRunning, lines);

            // 3. 阻塞等待进程返回
            logger.info("waiting for executing.....");
            try {
                int exitCode = waitExit(process, timeout);
                logger.info("exit code:" + exitCode);
                if (exitCode != 0) {
                    throw new RuntimeException();
                }

                return new ProcessResult().exitCode(exitCode).stdout(lines);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        } finally {
            if (process != null) {
                isRunning.set(false);
                //4. 销毁进程资源
                process.destroy();
                if(stdout != null) {
                    stdout.close();
                    logger.info(cmd.get(0) + " exited");
                }
            }
        }
    }

    private static class Worker extends Thread {
        private final Process process;
        private Integer exitCode;

        private Worker(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
    }
}