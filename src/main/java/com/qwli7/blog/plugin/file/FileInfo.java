package com.qwli7.blog.plugin.file;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 11:33
 * 功能：blog8
 **/
public class FileInfo implements Serializable {

    private String path;

    private String filename;

    private FileExtension fileExtension;

    private FileType fileType;
    private long fileSize;

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "path='" + path + '\'' +
                ", filename='" + filename + '\'' +
                ", fileExtension=" + fileExtension +
                ", fileType=" + fileType +
                ", fileSize=" + fileSize +
                '}';
    }
}
