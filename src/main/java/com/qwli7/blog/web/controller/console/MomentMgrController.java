package com.qwli7.blog.web.controller.console;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("console")
@Controller
public class MomentMgrController {

    private final MomentService momentService;

    public MomentMgrController(MomentService momentService) {
        this.momentService = momentService;
    }

    @GetMapping("moments")
    public String index() {
        return "console/moment/index";
    }

    @GetMapping("moment/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Moment moment = momentService.getMomentForEdit(id);
        model.addAttribute(moment);
        return "console/moment/edit";
    }

}
