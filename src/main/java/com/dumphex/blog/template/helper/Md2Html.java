package com.dumphex.blog.template.helper;

import com.dumphex.blog.service.Markdown2Html;

public class Md2Html {

    private final Markdown2Html markdown2Html;

    public Md2Html(Markdown2Html markdown2Html) {
        this.markdown2Html = markdown2Html;
    }

    public String toHtml(String markdown) {
        return markdown2Html.toHtml(markdown);
    }
}
