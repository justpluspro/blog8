package com.qwli7.blog.service;

import com.qwli7.blog.CommentStrategy;
import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.entity.User;

/**
 * @author qwli7 
 * @date 2021/2/26 13:37
 * 功能：ConfigService
 **/
public interface ConfigService {

    /**
     * 用户认证
     * @param name name
     * @param password password
     * @return boolean
     */
    boolean authenticate(String name, String password);

    /**
     * 获取评论策略
     * @return CommentStrategy
     */
    CommentStrategy getCommentStrategy();

    /**
     * 更新用户密码
     * @param password password
     * @param oldPassword oldPassword
     */
    void updatePassword(String password, String oldPassword);

    /**
     * 获取系统配置
     * @return BlogConfig
     */
    BlogConfig getConfig();

    /**
     * 获取用户信息
     * @return User
     */
    User getUser();
}
