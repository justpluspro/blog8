package com.qwli7.blog.exception;

import com.qwli7.blog.Message;

public class LoginFailException extends RuntimeException {

    private final Message error;

    public LoginFailException(String code, String msg) {
        super(null, null, false, false);
        this.error = new Message(code, msg);
    }

    public Message getError() {
        return error;
    }
}
