package com.qwli7.blog.plugin.file;

import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private FileUtils(){
        super();
    }

    public static String getBaseName(String filePath) {
        if(StringUtils.isEmpty(filePath)) {
            return "";
        }
        return getBaseName(Paths.get(filePath));
    }

    public static String getBaseName(Path filePath) {
        String filename = filePath.getFileName().toString();
        if(StringUtils.isEmpty(filename)) {
            return "";
        }
        if(!filename.contains(".")) {
            return "";
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }

    public static String getFileExtension(String filePath) {
        if(StringUtils.isEmpty(filePath)) {
            return "";
        }
        return getFileExtension(Paths.get(filePath));
    }

    public static String getFileExtension(Path filePath) {
        String filename = filePath.getFileName().toString();
        if(StringUtils.isEmpty(filename)) {
            return "";
        }
        if(!filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".")+1);
    }
}
