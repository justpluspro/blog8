package com.qwli7.blog.template.data;

import java.util.Map;

public abstract class AbstractDataProvider<T> {

    protected String name;

    public AbstractDataProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract T queryData(Map<String, String> attributeMap);
}
