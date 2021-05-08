package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * 通用查询参数
 * @author liqiwen
 * @since 1.2
 */
public class CommonQueryParam extends AbstractQueryParam implements Serializable {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
