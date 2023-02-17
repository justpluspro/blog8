package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.GlobalConfig;
import com.qwli7.blog.entity.vo.ConfigBean;
import com.qwli7.blog.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author qwli7
 * @date 2023/2/17 17:40
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class ConfigApiController {


    private final ConfigService configService;

    public ConfigApiController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("configs")
    public ResponseEntity<GlobalConfig> loadAll() {
        GlobalConfig globalConfig = configService.loadConfig();
        return ResponseEntity.ok(globalConfig);
    }


    @PutMapping("config")
    public ResponseEntity<Boolean> updateConfig(@RequestBody ConfigBean configBean) {
        configService.updateConfig(configBean);
        return ResponseEntity.ok(true);
    }
}
