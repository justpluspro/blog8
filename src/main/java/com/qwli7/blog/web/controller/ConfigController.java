package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2021/2/26 14:26
 * 功能：blog
 **/
@RestController
@RequestMapping("api")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("configs")
    public BlogConfig getAllConfig() {
        return configService.getConfig();
    }
}
