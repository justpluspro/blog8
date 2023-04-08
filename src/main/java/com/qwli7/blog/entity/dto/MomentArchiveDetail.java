package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Moment;

import java.io.Serializable;
import java.util.List;

/**
 * @author qwli7
 * @date 2023/4/7 12:56
 * 功能：blog8
 **/
public class MomentArchiveDetail implements Serializable {

    private String archiveDate;

    private List<Moment> moments;

    public String getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(String archiveDate) {
        this.archiveDate = archiveDate;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }
}
