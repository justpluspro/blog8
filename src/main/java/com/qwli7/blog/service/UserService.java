package com.qwli7.blog.service;

import com.qwli7.blog.entity.User;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：UserService
 **/
public interface UserService {

    /**
     * 获取用户数据
     * @return User
     */
    User getUser();

    /**
     * 更新密码
     */
    void updatePassword();


    /**
     * 修改用户数据
     */
    void updateUser();

}
