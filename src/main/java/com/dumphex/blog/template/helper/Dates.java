package com.dumphex.blog.template.helper;

import com.dumphex.blog.util.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * @author qwli7
 * @date 2021/3/4 8:23
 * 功能：blog
 **/
public class Dates {

    public String format(Temporal temporal, String pattern) {
        return TimeUtils.format(temporal, pattern);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public LocalDate nowDate() {
        return LocalDate.now();
    }

    public String nowYear() {
        final String format = format(now(), "yyyy-MM-dd");
        return format.substring(0, format.indexOf("-"));
    }



    public long millis() {
        return System.currentTimeMillis();
    }

    public LocalDateTime parse(String text, String pattern) {
        return TimeUtils.parse(text, pattern);
    }
}
