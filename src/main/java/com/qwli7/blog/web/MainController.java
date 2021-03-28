package com.qwli7.blog.web;

import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final MomentService momentService;

    public MainController(MomentService momentService) {
        this.momentService = momentService;
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }


    @GetMapping("moments")
    public String moments(MomentQueryParam queryParam) {

        return "moments";
    }
}
