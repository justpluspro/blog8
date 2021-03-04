package com.qwli7.blog.template.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Jsoups {
    public Jsoups() {

    }

    public Document body(String bodyHtml) {
        return Jsoup.parse(bodyHtml);
    }

    public Document body(String bodyHtml, String baseUri) {
        return Jsoup.parse(bodyHtml, baseUri);
    }

}
