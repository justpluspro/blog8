package com.qwli7.blog;

import java.io.Serializable;

/**
 * 消息处理
 * @author liqiwen
 * @since 1.2
 */
public class Message implements Serializable {

    /**
     * 业务码
     */
    private String code;

    /**
     * 业务消息
     */
    private String message;

    public Message(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Message() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
