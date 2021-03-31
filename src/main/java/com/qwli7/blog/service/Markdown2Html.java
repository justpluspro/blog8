package com.qwli7.blog.service;

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
    Map<Integer, String> toHtmls(Map<Integer, String> markdownMap);

    /**
     * 单个解析接口
     * @param markdown markdown
     * @return String
     */
    String toHtml(String markdown);
}
