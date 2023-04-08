package com.qwli7.blog.web.controller.api;

import com.google.common.util.concurrent.RateLimiter;
import com.qwli7.blog.BlogContext;
import com.qwli7.blog.consts.Consts;
import com.qwli7.blog.core.security.AttemptLogger;
import com.qwli7.blog.core.security.AttemptLoggerManager;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.entity.dto.LoginDto;
import com.qwli7.blog.entity.vo.LoginBean;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.LoginFailedException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.service.UserService;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qwli7 
 * @date 2023/2/16 15:12
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class LoginApiController implements InitializingBean {

    private final UserService userService;

    private final Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

    private final RateLimiter rateLimiter = RateLimiter.create(1);

    @Value("${login.attempt.count:5}")
    private Integer attemptCount;

    @Value("${login.attempt.max-count:10}")
    private Integer maxAttemptCount;


    private AttemptLogger attemptLogger;

    private final AttemptLoggerManager attemptLoggerManager;

    public LoginApiController(UserService userService, AttemptLoggerManager attemptLoggerManager) {
        this.userService = userService;
        this.attemptLoggerManager = attemptLoggerManager;
    }

    /**
     * 登录接口
     * <p>
     *     1. 相同 ip 尝试次数达到n次需要输入验证码
     *     2. 不同 ip 尝试次数达到最大总次数，需要输入验证码
     * </p>
     *
     * @param loginBean loginBean
     * @param request request
     * @return void
     */
    @PostMapping("login")
    public ResponseEntity<Boolean> login(@RequestBody @Validated LoginBean loginBean,
                                          HttpServletRequest request) {
        boolean tryAcquire = rateLimiter.tryAcquire();
        if (!tryAcquire) {
            throw new LoginFailedException(Message.OPERATOR_TOO_FREQUENCY);
        }

        String ip = BlogContext.getIp();
        // 记录ip并返回是否需要校验验证码请求
        if(attemptLogger.log(ip)) {
            checkCaptcha(loginBean, request);
        }
        if(attemptLogger.reach(ip)) {

        }
        User user = userService.login(loginBean);
        afterLoginSuccess(user, request);
        return ResponseEntity.ok(true);
    }

    private void afterLoginSuccess(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(Consts.USER, user);
        attemptLogger.remove(BlogContext.getIp());
        BlogContext.setAuthorized(true);
    }


    @GetMapping("login/needCaptcha")
    @ResponseBody
    public ResponseEntity<Boolean> needCaptcha() {
        String ip = BlogContext.getIp();
        Boolean flag = attemptLogger.reach(ip);
        return ResponseEntity.ok(flag);
    }


    /**
     * 登出接口
     * @param session session
     * @return void
     */
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        if(!BlogContext.isAuthorized()) {
            throw new BizException(Message.NOT_LOGIN);
        }
        session.removeAttribute(Consts.USER);
        session.invalidate();
        BlogContext.removeAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * 校验请求中的验证码是否正确
     * @param loginBean loginBean
     * @param request request
     */
    private void checkCaptcha(LoginBean loginBean, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String captchaCode = loginBean.getCaptchaCode();
        if(StringUtils.isEmpty(captchaCode)) {
            throw new BizException(Message.CAPTCHA_NOT_EMPTY);
        }
        String captcha = (String) session.getAttribute("captcha");
        if(captcha.equals(captchaCode)) {
            throw new BizException(Message.CAPTCHA_ERROR);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.attemptLogger = attemptLoggerManager.createAttemptLogger(attemptCount, maxAttemptCount);
    }
}
