package com.qwli7.blog.exception;

import com.qwli7.blog.Message;

/**
 * 逻辑异常
 * @author liqiwen
 * @since 2.0
 */
public class LogicException extends RuntimeException {

    /**
     * 错误信息
     */
    private final Message error;

    public LogicException(String code, String message) {
        super(message);
        this.error = new Message(code, message);
    }

    public Message getError() {
        return error;
    }
}
