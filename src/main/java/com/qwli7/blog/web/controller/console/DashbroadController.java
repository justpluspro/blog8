package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qwli7
 * @date 2023/2/20 20:36
 * 功能：blog8
 **/
@Controller
public class DashbroadController {

    @GetMapping("console/dashbroad")
    public String dashbroad(Model model) {


        return "/console/dashbroad";
    }
}
