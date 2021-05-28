package com.qwli7.blog.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * 文件上传模型
 * @author liqiwen
 * @since 1.2
 */
public class FileUploadModel implements Serializable {

    /**
     *  文件上传存储路径
     */
    private String path;

    /**
     * 待上传的文件
     */
    private List<MultipartFile> files;
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
