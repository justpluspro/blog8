package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * 修改密码
 * @author liqiwen
 * @since 1.2
 */
public class PasswordModel implements Serializable {

    private String oldPassword;

    private String password;

    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
