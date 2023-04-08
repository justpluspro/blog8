package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:52
 * 功能：blog8
 **/
public class CategoryQueryParams implements Serializable {

    private Boolean queryPrivate;

    public Boolean isQueryPrivate() {
        return queryPrivate;
    }

    public void setQueryPrivate(Boolean queryPrivate) {
        this.queryPrivate = queryPrivate;
    }

}
