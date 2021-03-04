package com.qwli7.blog.util;


import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TimeUtils {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Set<String> patterns = new HashSet<>(Arrays.asList("yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "HH:mm:ss", "HH:mm"));


    private TimeUtils() {
        super();
    }

    public static String format(Temporal temporal, String pattern) {
        if(StringUtils.isEmpty(pattern) || !patterns.contains(pattern)) {
            pattern = STANDARD_FORMAT;
        }
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }

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
}
