package com.qwli7.blog.exception;

/**
 * @author qwli7 
 * @date 2023/2/16 15:21
 * 功能：blog8
 **/
public class BizException extends RuntimeException {

    protected Message error;

    public BizException(Message error) {
        super(String.format("%s[%s]", error.getDesc(), error.getCode()));
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
