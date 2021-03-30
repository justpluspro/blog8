package com.qwli7.blog.file;

import com.qwli7.blog.entity.vo.AbstractQueryParam;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件查询参数
 * @author liqiwen
 * @since 1.2
 */
public class FileQueryParam extends AbstractQueryParam implements Serializable {

    /**
     * 查询路径
     */
    private String path;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 扩展名
     */
    private String extension;

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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
