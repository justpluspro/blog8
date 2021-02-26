package com.qwli7.blog.service;

import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.entity.User;

/**
 * @author qwli7 
 * @date 2021/2/26 13:37
 * 功能：blog
 **/
public interface ConfigService {

    boolean authenticate(String name, String password);


    void updatePassword(String password, String oldPassword);

    BlogConfig getConfig();

    User getUser();
}
