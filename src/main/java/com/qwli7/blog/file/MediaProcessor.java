package com.qwli7.blog.file;

import java.util.Arrays;
import java.util.List;

/**
 * @author qwli7 
 * 2021/3/2 9:05
 * 功能：MediaProcessor
 **/
public class MediaProcessor {

    private MediaProcessor() {
        super();
    }

    /**
     * 图片扩展名
     */
    public static final List<String> extensions = Arrays.asList("img", "mp4");

    /**
     * 文件是否能被处理
     * 图片：
     * 1. 裁剪
     * 2. 水印
     * 3. 缩放
     * 4. 去 exif
     *
     * 视频：
     * 1. 格式转换
     * 2. 压缩
     * 3. 截图
     * 4. 去 exif
     */
    public static boolean canHandle(String ext) {
        return extensions.contains(ext);
    }
}
