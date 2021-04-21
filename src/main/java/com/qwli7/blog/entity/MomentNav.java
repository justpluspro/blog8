package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * 动态导航
 * @author liqiwen
 * @since 1.2
 */
public class MomentNav implements Serializable {

    /**
     * 上一篇动态
     */
    private Moment prevMoment;

    /**
     * 下一篇动态
     */
    private Moment nextMoment;

    public MomentNav() {
        super();
    }

    public MomentNav(Moment prevMoment, Moment nextMoment) {
        super();
        this.nextMoment = nextMoment;
        this.prevMoment = prevMoment;
    }


    public void setPrevMoment(Moment prevMoment) {
        this.prevMoment = prevMoment;
    }

    public Moment getPrevMoment() {
        return prevMoment;
    }

    public void setNextMoment(Moment nextMoment) {
        this.nextMoment = nextMoment;
    }

    public Moment getNextMoment() {
        return nextMoment;
    }
}
