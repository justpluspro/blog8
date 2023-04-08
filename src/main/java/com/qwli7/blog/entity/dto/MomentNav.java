package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Moment;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/4/7 23:52
 * 功能：blog8
 **/
public class MomentNav implements Serializable {

    private Moment preMoment;

    private Moment currentMoment;

    private Moment nextMoment;

    public Moment getCurrentMoment() {
        return currentMoment;
    }

    public void setCurrentMoment(Moment currentMoment) {
        this.currentMoment = currentMoment;
    }

    public Moment getPreMoment() {
        return preMoment;
    }

    public void setPreMoment(Moment preMoment) {
        this.preMoment = preMoment;
    }

    public Moment getNextMoment() {
        return nextMoment;
    }

    public void setNextMoment(Moment nextMoment) {
        this.nextMoment = nextMoment;
    }
}
