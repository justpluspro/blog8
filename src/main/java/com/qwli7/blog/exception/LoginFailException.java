package com.qwli7.blog.exception;

import com.qwli7.blog.Message;

/**
 * 登录失败异常
 * @author liqiwen
 * @since 1.2
 */
public class LoginFailException extends RuntimeException {

    /**
     * 错误信息
     */
    private final Message error;

    public LoginFailException(String code, String msg) {
        super(null, null, false, false);
        this.error = new Message(code, msg);
    }

    public Message getError() {
        return error;
    }
}
