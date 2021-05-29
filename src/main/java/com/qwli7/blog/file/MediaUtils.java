package com.qwli7.blog.file;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 媒体处理工具类
 * @author liqiwen
 * @since 2.3
 */
public class MediaUtils {
    private static final List<String> canHandleImageExts = Arrays.asList("jpg", "jpeg", "png");
    private static final List<String> canHandleVideoExts = Arrays.asList("mp4", "rmvb");

    public static boolean canHandleImage(String ext) {
        if(StringUtils.isEmpty(ext)) {
            return false;
        }
        return canHandleImageExts.contains(ext.toLowerCase());
    }

    public static boolean canHandleVideo(String ext) {
        if(StringUtils.isEmpty(ext)) {
            return false;
        }
        return canHandleVideoExts.contains(ext.toLowerCase());
    }
}
