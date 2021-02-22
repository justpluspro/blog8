package com.qwli7.blog.entity;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2021/2/22 13:06
 * 功能：BaseEntity
 **/
public abstract class BaseEntity {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
