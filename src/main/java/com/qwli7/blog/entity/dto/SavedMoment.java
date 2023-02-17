package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.enums.MomentState;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:55
 * 功能：blog8
 **/
public class SavedMoment implements Serializable {

    private Integer id;

    private MomentState state;

    public SavedMoment(Moment moment) {
        this.id = moment.getId();
        this.state = moment.getState();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MomentState getState() {
        return state;
    }

    public void setState(MomentState state) {
        this.state = state;
    }
}
