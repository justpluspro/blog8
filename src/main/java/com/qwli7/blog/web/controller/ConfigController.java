package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author qwli7 
 * 2021/2/26 14:26
 * 功能：ConfigController
 **/
@Authenticated
@RestController
@RequestMapping("api")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 获取全部的配置
     * @return BlogConfig
     */
    @GetMapping("configs")
    public BlogConfig getAllConfig() {
        return configService.getConfig();
    }

    /**
     * 修改配置信息
     * @param blogConfig blogConfig
     */
    @PutMapping("config")
    public ResponseEntity<?> updateConfig(@RequestBody BlogConfig blogConfig) {
        configService.updateConfig(blogConfig);
        return ResponseEntity.noContent().build();
    }
}
