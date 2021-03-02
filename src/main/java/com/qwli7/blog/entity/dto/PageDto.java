package com.qwli7.blog.entity.dto;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/3/2 10:15
 * 功能：blog
 **/
public class PageDto implements Serializable {

    private Integer page;

    private Integer size;

    private Object data;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
