package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.enums.MomentStatus;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:52
 * 功能：blog8
 **/
public class MomentQueryParams extends PageQueryParams implements Serializable {

    private String query;

    private Boolean queryPrivate;

    private MomentStatus status;

    public Boolean getQueryPrivate() {
        return queryPrivate;
    }

    public void setQueryPrivate(Boolean queryPrivate) {
        this.queryPrivate = queryPrivate;
    }

    public MomentStatus getStatus() {
        return status;
    }

    public void setStatus(MomentStatus status) {
        this.status = status;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
