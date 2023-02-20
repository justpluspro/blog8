package com.qwli7.blog.plugin.file;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/20 13:21
 * 功能：blog8
 **/
public enum FileExtension {

    JPG("jpg"),
    PNG("png"),
    JPEG("jpeg"),
    GIF("gif"),
    WBEP("webp"),

    MP4("mp4"),
    MOV("mov"),
    RM("rm"),
    RMVB("rmvb"),

    MARKDOWN("md"),
    TXT("txt"),
    CSS("css"),
    XML("xml"),
    JSON("json"),
    JAVASCRIPT("js"),
    HTML("html"),
    HTM("htm"),


    ;


    final String ext;

    FileExtension(String ext) {
        this.ext = ext;
    }

    public static Optional<FileExtension> convert(String ext) {
        if(StringUtils.isEmpty(ext)) {
            return Optional.empty();
        }
        return Arrays.stream(FileExtension.values()).filter(_e -> _e.getExt().equals(ext.toLowerCase())).findFirst();
    }

    public String getExt() {
        return ext;
    }
}
