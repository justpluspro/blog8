package com.qwli7.blog.entity.vo;

/**
 * @author qwli7 
 * @date 2021/3/2 10:17
 * 功能：blog
 **/
public class MomentQueryParam extends AbstractQueryParam {
    private String query;

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
