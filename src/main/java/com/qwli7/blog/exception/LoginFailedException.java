package com.qwli7.blog.exception;

/**
 * @author qwli7
 * @date 2023/2/21 13:13
 * 功能：blog8
 **/
public class LoginFailedException extends RuntimeException {

    protected Message error;

    public LoginFailedException(Message error) {
        super(String.format("%s[%s]", error.getDesc(), error.getCode()));
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
