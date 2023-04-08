package com.qwli7.blog.entity.dto;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/3/6 16:07
 * 功能：blog8
 **/
public class LoginDto implements Serializable {

    private Boolean state;

    private Boolean captcha;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Boolean captcha) {
        this.captcha = captcha;
    }
}
