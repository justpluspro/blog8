package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7 
 * 2021/3/2 10:17
 * 功能：动态查询参数
 **/
public class MomentQueryParam extends AbstractQueryParam implements Serializable {

    /**
     * 关键字
     */
    private String query;

    /**
     * 是否倒序排列
     */
    private Boolean orderDesc;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(Boolean orderDesc) {
        this.orderDesc = orderDesc;
    }
}
