package com.qwli7.blog;

import java.io.Serializable;

public class Message implements Serializable {

    private String code;

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
