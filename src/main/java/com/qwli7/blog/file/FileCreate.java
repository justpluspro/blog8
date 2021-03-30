package com.qwli7.blog.file;

import java.io.Serializable;

/**
 * 创建文件
 * @author liqiwen
 * @since 1.2
 */
public class FileCreate implements Serializable {

    /**
     * 文件创建路径
     */
    private String path;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private FileType fileType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
