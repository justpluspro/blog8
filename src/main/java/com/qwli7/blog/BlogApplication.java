package com.qwli7.blog;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.queue.runnable.ArticlePostRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 启动类
 * @author liqiwen
 * @since 1.2
 */
@SpringBootApplication
@EnableScheduling
public class BlogApplication {

    public static void main(String[] args){
        SpringApplication.run(BlogApplication.class, args);
    }

    @Resource(name = "commentNotifyContainer")
    private DataContainer<Comment> dataContainer;

}
