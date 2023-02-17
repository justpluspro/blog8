package com.qwli7.blog.exception;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2023/2/17 9:05
 * 功能：blog8
 **/
public class ResourceNotFoundException extends RuntimeException {

    private final Message error;

    public ResourceNotFoundException(Message error) {
        super(String.format("%s[%s]", error.getDesc(), error.getCode()));
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
