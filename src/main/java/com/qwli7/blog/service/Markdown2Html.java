package com.qwli7.blog.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Markdown2Html 解析接口
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
public interface Markdown2Html {

    /**
     * 批量解析接口
     * @param markdownMap markdownMap
     * @return Map
     */
    default Map<Integer, String> toHtmls(Map<Integer, String> markdownMap) {
        Map<Integer, String> map = new HashMap<>();
        for(Map.Entry<Integer, String> it: markdownMap.entrySet()) {
            map.put(it.getKey(), toHtml(it.getValue()));
        }
        return map;
    }

    /**
     * 单个解析接口
     * @param markdown markdown
     * @return String
     */
    String toHtml(String markdown);
}
