package com.qwli7.blog.util;


import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 时间工具类
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
public class TimeUtils {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Set<String> patterns = new HashSet<>(Arrays.asList("yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "HH:mm:ss", "HH:mm"));


    private TimeUtils() {
        super();
    }

    /**
     * 格式化时间
     * @param temporal temporal
     * @param pattern pattern
     * @return String
     */
    public static String format(Temporal temporal, String pattern) {
        if(StringUtils.isEmpty(pattern) || !patterns.contains(pattern)) {
            pattern = STANDARD_FORMAT;
        }
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }

    /**
     * 格式化时间
     * @param text text
     * @param pattern pattern
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String text, String pattern) {
        if(StringUtils.isEmpty(pattern) || !patterns.contains(pattern)) {
            pattern = STANDARD_FORMAT;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    public static LocalDateTime parse(String text) {
        return parse(text, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取两个时间的之间的秒数
     * @param start start
     * @param end end
     * @return long
     */
    public static long getSeconds(LocalDateTime start, LocalDateTime end) {
        if(start == null || end == null || end.isBefore(start)) {
            return -1;
        }
        long startMills = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endMills = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long millsInterval = endMills - startMills;
        return millsInterval/1000;
    }

}
