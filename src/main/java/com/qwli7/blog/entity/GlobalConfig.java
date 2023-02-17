package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:25
 * 功能：全局配置
 **/
public class GlobalConfig implements Serializable {

    private Boolean initial;

    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }
}
