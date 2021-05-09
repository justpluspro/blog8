package com.qwli7.blog.entity;

import com.qwli7.blog.CommentStrategy;

import java.io.Serializable;

/**
 * @author qwli7
 * 2021/2/26 13:55
 * 功能：BlogConfig
 **/
public class BlogConfig implements Serializable {

    public BlogConfig() {
        super();
    }

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 默认头像
     */
    private String defaultAvatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;


    /**
     * 评论审核策略
     */
    private CommentStrategy commentStrategy;

    public CommentStrategy getCommentStrategy() {
        return commentStrategy;
    }

    public void setCommentStrategy(CommentStrategy commentStrategy) {
        this.commentStrategy = commentStrategy;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
