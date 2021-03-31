package com.qwli7.blog;

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
public class Blog {

    public static void main(String[] args){
        SpringApplication.run(Blog.class, args);
    }
}
