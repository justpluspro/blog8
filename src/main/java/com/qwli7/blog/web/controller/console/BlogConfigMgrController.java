package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("console")
public class BlogConfigMgrController {


    @GetMapping("configs")
    public String index() {
        return "console/config/index";
    }
}
