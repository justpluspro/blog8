package com.qwli7.blog.template.data;

import java.util.Map;

public abstract class DataProvider<T> {

    protected String name;

    public DataProcessor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract T queryData(Map<String, String> attributeMap);
}
