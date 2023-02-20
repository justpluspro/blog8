package com.qwli7.blog;

import com.qwli7.blog.service.ConfigService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 启动类
 * @author liqiwen
 * @since 1.2
 */
@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    private final ConfigService configService;

    public Application(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void run(String... args) throws Exception {
        configService.loadConfig();
    }
}
