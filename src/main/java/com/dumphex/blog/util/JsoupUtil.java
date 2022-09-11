package com.dumphex.blog.util;

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

    /**
     * 获取第一个 img 属性的 src
     * @param html html
     * @return String
     */
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

    /**
     * 获取第一个视频的封面
     * @param html html
     * @return String
     */
    public static Optional<String> getFirstVideoPoster(String html) {
        if(StringUtils.isEmpty(html)) {
            return Optional.empty();
        }
        final Element video = Jsoup.parse(html).selectFirst("video");
        if(video == null) {
            return Optional.empty();
        }
        final String poster = video.attr("poster");
        if(StringUtils.isEmpty(poster)) {
            return Optional.empty();
        }
        return Optional.of(poster);
    }
}
