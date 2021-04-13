package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.entity.PasswordModel;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：UserController
 **/
@Authenticated
@RestController
@RequestMapping("api")
public class UserController {

    private final ConfigService configService;

    public UserController(ConfigService configService) {
        this.configService = configService;
    }


    /**
     * 获取用户信息
     * @return user
     */
    @GetMapping("user")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.of(Optional.of(configService.getUser()));
    }

    /**
     * 修改用户信息
     * @param user user
     * @return ResponseEntity
     */
    @PutMapping("user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        configService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    /**
     * 修改密码
     * @param passwordModel passwordModel
     * @return ResponseEntity
     */
    @PutMapping("user/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordModel passwordModel) {
        configService.updatePassword(passwordModel.getPassword(), passwordModel.getOldPassword());
        return ResponseEntity.ok().build();
    }

}
