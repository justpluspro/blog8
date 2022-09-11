package com.dumphex.blog.file.vo;

/**
 * actionEnum
 * 参数转换枚举
 */
public enum ActionEnum {

    /**
     * 视频转视频
     */
    VIDEO2VIDEO("video2video", "视频转视频"),

    /**
     * 视频转图片
     */
    VIDEO2IMG("video2img", "视频转图片"),

    /**
     * 视频转 gif
     */
    VIDEO2GIF("video2gif", "视频转 gif"),

    /**
     * 图片转图片
     */
    IMG2IMG("img2img", "图片转图片"),

        ;

    private final String code;

    private final String name;

    ActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
