package com.qwli7.blog.entity.vo;

/**
 * @author qwli7
 * @date 2021/3/2 10:16
 * 功能：blog
 **/
public class AbstractQueryParam {
    private int page;

    private int size;

    private int start;

    private int offset;

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getOffset() {
        return getSize();
    }

    public int getStart() {
        return (getPage()-1) * getSize();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
