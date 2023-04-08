package com.qwli7.blog.plugin.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/4/7 22:26
 * 功能：blog8
 **/
public class JsoupUtil {

    private JsoupUtil() {
        super();
    }

    /**
     * 获取第一个 img 标签的 src 属性
     * @param html html
     * @return String
     */
    public static Optional<String> getFirstImage(String html) {
        Document document = Jsoup.parse(html);
        Element element = document.select("img").first();
        if(element == null) {
            return Optional.empty();
        }
        String src = element.attr("src");
        if(StringUtils.isEmpty(src)) {
            return Optional.empty();
        }
        return Optional.of(src);
    }


    /**
     * 获取第一个 video 标签的 src 属性
     * @param html html
     * @return String
     */
    public static Optional<String> getFirstVideo(String html) {
        Document document = Jsoup.parse(html);
        Element element = document.select("video").first();
        if(element == null) {
            return Optional.empty();
        }
        String src = element.attr("src");
        if(StringUtils.isEmpty(src)) {
            return Optional.empty();
        }
        return Optional.of(src);
    }

    /**
     * 清除 html 标签，过滤 xss 注入
     * @param html html
     * @return String
     */
    public static String clean(String html) {
        return Jsoup.clean(html, Safelist.basic());
    }
}
