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
    private Integer code;

    /**
     * 请求 message
     */
    private String message;

    /**
     * 请求数据
     */
    private Object data;


    public boolean isSuccess() {
        return 200 == this.code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
