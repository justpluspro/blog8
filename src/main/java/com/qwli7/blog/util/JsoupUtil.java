package com.qwli7.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author qwli7
 * 2021/3/16 13:46
 * 功能：JsoupUtil
 **/
public class JsoupUtil {

    private JsoupUtil() {
        super();
    }

    public static Optional<String> getFirstImage(String html) {
        if(StringUtils.isEmpty(html)) {
            return Optional.empty();
        }

        Element img = Jsoup.parse(html).selectFirst("img");
        if(img == null) {
            return Optional.empty();
        }

        return Optional.of(img.attr("src"));
    }
}
