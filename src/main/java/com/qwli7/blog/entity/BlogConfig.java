package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/26 13:55
 * 功能：blog
 **/
public class BlogConfig implements Serializable {

    public BlogConfig() {
        super();
    }

    public BlogConfig(BlogConfig blogConfig) {
        super();
        this.password = blogConfig.getPassword();
        this.loginName = blogConfig.getLoginName();
        this.email = blogConfig.getEmail();
        this.nickname = blogConfig.getNickname();
        this.avatar = blogConfig.getAvatar();
    }

    private String avatar;

    private String loginName;

    private String nickname;

    private String defaultAvatar;

    private String email;

    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    public void setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
