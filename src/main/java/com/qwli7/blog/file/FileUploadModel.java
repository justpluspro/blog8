package com.qwli7.blog.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

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
    private MultipartFile file;
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
