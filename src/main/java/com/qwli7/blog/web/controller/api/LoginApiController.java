package com.qwli7.blog.web.controller.api;

import com.google.common.util.concurrent.RateLimiter;
import com.qwli7.blog.BlogContext;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.entity.vo.LoginBean;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private RateLimiter rateLimiter = RateLimiter.create(1);

    public LoginApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录接口
     * @param loginBean loginBean
     * @param session session
     * @return void
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody @Validated LoginBean loginBean,
                                      HttpSession session) {
        boolean tryAcquire = rateLimiter.tryAcquire();
        if (!tryAcquire) {
            throw new BizException(Message.OPERATOR_TOO_FREQUENCY);
        }
        User user = userService.login(loginBean);
        session.setAttribute("user", user);
        BlogContext.setAuthorized(true);
        return ResponseEntity.noContent().build();


    }
}
