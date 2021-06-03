package com.qwli7.blog.entity.dto;

import java.io.Serializable;

/**
 * ResultDto
 * @author liqiwen
 * @since 2.0
 */
public class ResultDto implements Serializable {

    /**
     * 业务状态码
     */
    private boolean success;

    /**
     * 请求 message
     */
    private String msg;

    /**
     * 请求数据
     */
    private String data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
