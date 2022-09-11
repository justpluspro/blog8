package com.dumphex.blog.file.converter;

/**
 * 转换动作
 * @author liqiwen
 * @since 2.4
 */
public enum ConvertAction {
    /**
     * 图片转图片
     */
    IMG2IMG("img2img"),

    /**
     * 视频转图片
     * 一般是获取首帧
     */
    VIDEO2IMG("video2Img"),

    /**
     * 视频转视频
     */
    VIDEO2VIDEO("video2video"),

    /**
     * 加水印
     */
    WATERMARK("watermark"),

    /**
     * 视频转 gif
     */
    VIDEO2GIF("video2gif"),

    /**
     * 元数据
     */
    METADATA("metadata"),
    ;

    String action;

    ConvertAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
