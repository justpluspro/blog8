package com.dumphex.blog.template.data;

import java.util.Map;

/**
 * 数据提取 Provider
 * @param <T> T
 */
public abstract class AbstractDataProvider<T> {
    /**
     * 名称
     */
    protected String name;

    public AbstractDataProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 查询数据
     * @param attributeMap attributeMap
     * @return T
     */
    public abstract T queryData(Map<String, String> attributeMap);
}
