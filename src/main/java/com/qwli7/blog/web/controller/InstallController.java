package com.qwli7.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qwli7
 * @date 2023/2/17 17:35
 * 功能：blog8
 **/
@Controller
public class InstallController {


    @GetMapping("install")
    public String initial() {

        return "initial";
    }
}
