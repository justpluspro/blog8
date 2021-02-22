package com.qwli7.blog.exception;

import com.qwli7.blog.Message;

public class LogicException extends RuntimeException {

    private final Message error;

    public LogicException(String code, String message) {
        super(message);
        this.error = new Message(code, message);
    }

    public Message getError() {
        return error;
    }
}
