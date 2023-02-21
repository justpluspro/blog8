package com.qwli7.blog.plugin.file;

import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Array;
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


    public static boolean canEdit(String ext) {
        Optional<FileExtension> fileExtensionOp = convert(ext);
        if(fileExtensionOp.isPresent()) {
            if (Arrays.asList(FileExtension.HTM, FileExtension.HTML, FileExtension.CSS,
                    FileExtension.MARKDOWN, FileExtension.JSON,
                    FileExtension.JAVASCRIPT, FileExtension.XML).contains(fileExtensionOp.get()));
        }
        return false;
    }


    public String getExt() {
        return ext;
    }
}
