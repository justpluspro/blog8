package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.entity.vo.LoginModel;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.security.TokenUtil;
import com.qwli7.blog.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：登录控制器
 **/
@RestController
@RequestMapping("api")
@Validated
public class LoginController {

    private final ConfigService configService;

    public LoginController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 用户登录
     * @param loginModel loginModel
     * @return ResponseEntity
     */
    @PostMapping("token")
    public ResponseEntity<?> session(@RequestBody @Valid LoginModel loginModel){
        final String username = loginModel.getUsername();
        final String password = loginModel.getPassword();
        boolean authenticate = configService.authenticate(username, password);
        BlogContext.setAuthenticated(authenticate);
        final String token = TokenUtil.createNew();
        return ResponseEntity.ok(token);
    }

    /**
     * 用户登出
     * @return ResponseEntity
     */
    @Authenticated
    @PostMapping("logout")
    public ResponseEntity<Void> logout() {
        BlogContext.setAuthenticated(false);
        TokenUtil.remove();
        return ResponseEntity.noContent().build();
    }
}
