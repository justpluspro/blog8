package com.qwli7.blog;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.queue.runnable.ArticlePostRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
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
public class Blog implements ApplicationListener<ContextClosedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final ScheduledExecutorService scheduledExecutorService;

    public Blog(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }


    @Resource(name = "commentNotifyContainer")
    private DataContainer<Comment> dataContainer;

    public static void main(String[] args){
        SpringApplication.run(Blog.class, args);


    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        logger.info("Context Closed");
        List<Comment> commentList = new ArrayList<>();
        while (!dataContainer.isEmpty()) {
            // 持久化到磁盘
            commentList.add(dataContainer.pop());
        }
        final List<Runnable> runnables = scheduledExecutorService.shutdownNow();
        if(runnables.isEmpty()) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        runnables.forEach(e -> {
            if(e instanceof ArticlePostRunnable) {
                ArticlePostRunnable articlePostRunnable = (ArticlePostRunnable) e;
                ids.add(articlePostRunnable.getArticle().getId());
            }
        });
        if(ids.size() > 0) {
            System.out.println("未发布的文章");
        }
    }
}
