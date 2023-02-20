package com.qwli7.blog.plugin.file;

import com.qwli7.blog.entity.vo.PageQueryParams;

public class FileQueryParams extends PageQueryParams {

    private String query;

    private String queryPath;

    private boolean containDir;

    public String getQueryPath() {
        return queryPath;
    }

    public void setQueryPath(String queryPath) {
        this.queryPath = queryPath;
    }

    public boolean isContainDir() {
        return containDir;
    }

    public void setContainDir(boolean containDir) {
        this.containDir = containDir;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
