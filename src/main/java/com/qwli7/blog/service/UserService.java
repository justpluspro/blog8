package com.qwli7.blog.service;

import com.qwli7.blog.entity.User;
import com.qwli7.blog.entity.vo.LoginBean;
import com.qwli7.blog.exception.BizException;

/**
 * @author qwli7 
 * @date 2023/2/16 14:51
 * 功能：blog8
 **/
public interface UserService {

    void getUser();

    /**
     * login
     * @param loginBean loginBean
     */
    User login(LoginBean loginBean) throws BizException;
}
