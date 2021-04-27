package com.qwli7.blog.util;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * 流工具处理类
 * @author liqiwen
 * @since 2.0
 */
public class StreamUtils {

    private StreamUtils() {
        super();
    }

    /**
     * Resource To String
     * @param resource resource
     * @return String
     */
    public static String readResourceToString(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return org.springframework.util.StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * FileToString
     * @param file file
     * @return String
     */
    public static String readFileToString(File file) {
        if(!file.exists() || file.isDirectory()) {
            return null;
        }
        try {
            return StringUtils.collectionToDelimitedString(
                    Files.readAllLines(file.toPath(), StandardCharsets.UTF_8), "\r\n");
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
