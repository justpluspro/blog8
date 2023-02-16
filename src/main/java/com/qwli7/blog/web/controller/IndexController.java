package com.qwli7.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qwli7 
 * @date 2023/2/16 15:54
 * 功能：blog8
 **/
@Controller
public class IndexController {


    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
