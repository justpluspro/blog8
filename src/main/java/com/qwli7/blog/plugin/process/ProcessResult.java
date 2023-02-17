package com.qwli7.blog.plugin.process;

import java.io.Serializable;
import java.util.List;

/**
 * @author qwli7
 */
public class ProcessResult implements Serializable {
    /**
     * 退出状态码
     */
    private int exitCode;

    /**
     * 标准输出
     */
    private List<String> stdout;

    /**
     * 错误输出
     */
    private List<String> stderr;

    public ProcessResult exitCode(final int exitCode) {
        this.exitCode = exitCode;
        return this;
    }

    public ProcessResult stdout(final List<String> stdout) {
        this.stdout = stdout;
        return this;
    }

    public ProcessResult stderr(final List<String> stderr) {
        this.stderr = stderr;
        return this;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public List<String> getStdout() {
        return stdout;
    }

    public void setStdout(List<String> stdout) {
        this.stdout = stdout;
    }

    public List<String> getStderr() {
        return stderr;
    }

    public void setStderr(List<String> stderr) {
        this.stderr = stderr;
    }
}
