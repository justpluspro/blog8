package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/22 13:05
 * 功能：blog8
 **/
public class User implements Serializable {

    private String username;

    private String password;

    private String email;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
