package com.dumphex.blog.file.vo;

/**
 * 添加水印控制参数
 * @author liqiwen
 * @since 2.2
 */
public class WatermarkTextControlArgs extends ControlArgs {

    /**
     * 要添加水印的文本
     */
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
