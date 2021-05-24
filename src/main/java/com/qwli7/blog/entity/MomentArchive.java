package com.qwli7.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 动态归档
 * @author liqiwen
 * 按天归档
 */
public class MomentArchive implements Serializable {

    /**
     * 归档日期
     */
    private LocalDate archiveDate;

    /**
     * 动态列表
     */
    private List<Moment> moments;

    public LocalDate getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(LocalDate archiveDate) {
        this.archiveDate = archiveDate;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }
}
