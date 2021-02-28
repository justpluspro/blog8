package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("console")
@Controller
public class MomentMgrController {

    @GetMapping("moments")
    public String index() {
        return "console/moment/index";
    }

}
