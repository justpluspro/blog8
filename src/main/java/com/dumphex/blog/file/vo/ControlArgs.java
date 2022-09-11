package com.dumphex.blog.file.vo;

import java.io.File;
import java.io.Serializable;

/**
 * 转换参数
 * @author liqiwen
 * @since 2.2
 */
public class ControlArgs implements Serializable {

    private String action;

    private File inputFile;

    private File outputFile;

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
