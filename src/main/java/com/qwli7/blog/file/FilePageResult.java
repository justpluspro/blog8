package com.qwli7.blog.file;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/**
 * 文章分页参数结果
 * @author liqiwen
 * @since 1.2
 */
public class FilePageResult implements Serializable {

    private List<FileInfoDetail> fids;

    private Path path;

    public List<FileInfoDetail> getFids() {
        return fids;
    }

    public void setFids(List<FileInfoDetail> fids) {
        this.fids = fids;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
