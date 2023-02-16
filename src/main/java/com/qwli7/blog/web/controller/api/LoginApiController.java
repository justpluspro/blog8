package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.User;
import com.qwli7.blog.entity.vo.LoginBean;
import com.qwli7.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author qwli7 
 * @date 2023/2/16 15:12
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class LoginApiController {

    private final UserService userService;

    public LoginApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody @Validated LoginBean loginBean,
                                      HttpSession session) {
        User user = userService.login(loginBean);
        session.setAttribute("user", user);
        return ResponseEntity.noContent().build();
    }
}
