package com.qwli7.blog.file.converter;

/**
 * 转换动作
 * @author liqiwen
 * @since 2.4
 */
public enum ConvertAction {
    IMG2IMG("img2img"),
    VIDEO2IMG("video2Img");

    String action;

    ConvertAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
