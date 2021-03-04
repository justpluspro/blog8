package com.qwli7.blog.template.helper;

import com.qwli7.blog.util.TimeUtils;
import com.sun.tools.corba.se.idl.constExpr.Times;

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


    public long millis() {
        return System.currentTimeMillis();
    }

    public LocalDateTime parse(String text, String pattern) {
        return TimeUtils.parse(text, pattern);
    }
}
