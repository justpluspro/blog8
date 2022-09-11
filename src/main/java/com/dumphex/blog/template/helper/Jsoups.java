package com.dumphex.blog.template.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

/**
 * 解析 html 内容
 * @author liqiwen
 * @since 2.1
 */
public class Jsoups {
    public Jsoups() {

    }

    public Document body(String bodyHtml) {
        return Jsoup.parse(bodyHtml);
    }

    public Document body(String bodyHtml, String baseUri) {
        return Jsoup.parse(bodyHtml, baseUri);
    }

    public String parse(String bodyHtml, int length) {
        final Document document = body(bodyHtml);
        String text = document.text();
        if(StringUtils.isEmpty(text)) {
            return "";
        }
        if(text.length() > length) {
            text = text.substring(0, length) + "......";
        }
        return text;
    }

}
