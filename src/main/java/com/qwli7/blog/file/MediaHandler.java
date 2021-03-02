package com.qwli7.blog.file;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author qwli7 
 * @date 2021/3/2 9:05
 * 功能：blog
 **/
public class MediaHandler {


    public static final List<String> extensions = Arrays.asList("img", "mp4");

    public static boolean canHandle(String ext) {
        return extensions.contains(ext);
    }
}
