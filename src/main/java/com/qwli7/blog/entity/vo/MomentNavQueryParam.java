package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.enums.MomentStatus;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/4/7 23:55
 * 功能：blog8
 **/
public class MomentNavQueryParam implements Serializable {

    private Boolean queryPrivate;

    private Moment currentMoment;

    private MomentStatus status;

    public Boolean getQueryPrivate() {
        return queryPrivate;
    }

    public void setQueryPrivate(Boolean queryPrivate) {
        this.queryPrivate = queryPrivate;
    }

    public Moment getCurrentMoment() {
        return currentMoment;
    }

    public void setCurrentMoment(Moment currentMoment) {
        this.currentMoment = currentMoment;
    }

    public MomentStatus getStatus() {
        return status;
    }

    public void setStatus(MomentStatus status) {
        this.status = status;
    }
}
