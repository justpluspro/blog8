package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.User;
import com.qwli7.blog.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：UserController
 **/
@RestController
@RequestMapping("api")
public class UserController {

    private final ConfigService configService;

    public UserController(ConfigService configService) {
        this.configService = configService;
    }


    @GetMapping("user")
    public User getUser() {
        return configService.getUser();
    }
}
