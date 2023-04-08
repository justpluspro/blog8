package com.qwli7.blog.entity.vo;

import java.io.Serializable;

public class PageQueryParams implements Serializable {

    protected Integer page;

    protected Integer size;

    private Integer offsetStart;

    public Integer getPage() {
        if(page == null || page <= 0) {
            this.page = 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if(size == null || size < 10) {
            this.size = 10;
        }
        return size;
    }

    public Integer getOffsetStart() {
        return (getPage()-1) * getSize();
    }

    public void setOffsetStart(Integer offsetStart) {
        this.offsetStart = offsetStart;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
