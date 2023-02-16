package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 14:51
 * 功能：blog8
 **/
public class User implements Serializable {

    private String username;

    private String email;

    private String avatar;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
