package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.security.TokenUtil;
import com.qwli7.blog.service.ConfigService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

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
     * @param name name
     * @param password password
     * @return ResponseEntity
     */
    @PostMapping("token")
    public ResponseEntity<?> session(@NotBlank(message = "用户名不能为空") String name,
                                     @NotBlank(message = "密码不能为空") @Length(message = "密码长度不能小于 {min}", min = 6) String password){
        boolean authenticate = configService.authenticate(name, password);
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
