package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7
 * 2021/3/2 10:16
 * 功能：AbstractQueryParam 基础查询参数
 **/
public class AbstractQueryParam implements Serializable {

    /**
     * 当前页
     */
    private int page;

    /**
     * 页大小
     */
    private int size;

    /**
     * 开始位置
     */
    private int start;

    /**
     * 结束位置
     */
    private int offset;


    public boolean hasNoSize() {
        return this.size < 10;
    }

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
        return Math.max(this.page, 1);
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
