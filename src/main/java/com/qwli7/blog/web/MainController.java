package com.qwli7.blog.web;

import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("moments/archive")
    @ResponseBody
    public PageDto<MomentArchive> select(MomentQueryParam queryParam) {
        return momentService.selectArchivePage(queryParam);
    }
}
