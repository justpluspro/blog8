package com.qwli7.blog.plugin.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/17 11:32
 * 功能：blog8
 **/
public class FileUpload implements Serializable {

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
