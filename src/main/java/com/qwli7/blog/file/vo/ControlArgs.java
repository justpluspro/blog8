package com.qwli7.blog.file.vo;

import java.io.Serializable;

/**
 * 转换参数
 * @author liqiwen
 * @since 2.2
 */
public class ControlArgs implements Serializable {


    private String action;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
