package com.qwli7.blog;

import com.qwli7.blog.dao.CategoryDao;
import com.qwli7.blog.entity.Category;
import com.qwli7.blog.service.ConfigService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

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

    @Resource
    private CategoryDao categoryDao;

    private final ConfigService configService;

    public Application(ConfigService configService) {
        this.configService = configService;
    }


    @Override
    public void run(String... args) throws Exception {
        Category category = new Category();
        category.setName("java");
        category.setAlias("java");
        categoryDao.save(category);

        category = new Category();
        category.setAlias("web");
        category.setName("web");
        categoryDao.save(category);

        configService.loadConfig();
    }
}
