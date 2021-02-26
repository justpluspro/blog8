package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class LoginController {


    private final ConfigService configService;

    public LoginController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("token")
    public ResponseEntity<?> session(String name, String password){
        boolean authenticate = configService.authenticate(name, password);
        BlogContext.setAuthenticated(authenticate);
        return ResponseEntity.ok(authenticate);
    }
}
