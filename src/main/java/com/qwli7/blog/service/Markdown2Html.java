package com.qwli7.blog.service;

import java.util.Map;

public interface Markdown2Html {

    Map<Integer, String> toHtmls(Map<Integer, String> markdownMap);

    String toHtml(String markdown);
}
