package com.qwli7.blog;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.service.ConfigService;
import com.qwli7.blog.service.MomentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;


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
    private final MomentService momentService;

    public Application(ConfigService configService, MomentService momentService) {
        this.configService = configService;
        this.momentService = momentService;
    }

    @Override
    public void run(String... args) throws Exception {
        configService.loadConfig();

        for(int i = 0; i < 10; i++) {
            Moment moment = new Moment();
            moment.setContent("测试动态");
            momentService.addMoment(moment);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
