package com.qwli7.blog.task;

import com.qwli7.blog.component.EmailProcessor;
import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.service.ConfigService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 评论通知定时器
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
@Component
public class CommentNotifyScheduler {

    private final DataContainer<Comment> dataContainer;
    private final EmailProcessor emailProcessor;
    private final ConfigService configService;


    public CommentNotifyScheduler(DataContainer<Comment> dataContainer, EmailProcessor emailProcessor,
                                  ConfigService configService) {
        this.dataContainer = dataContainer;
        this.emailProcessor = emailProcessor;
        this.configService = configService;
    }


    @Scheduled(cron = "0/10 * * * * ?")
    public void commentNotify() {
        if(dataContainer.isEmpty()){
            return;
        }

        final Comment comment = dataContainer.pop();
        if(comment != null) {
            final Comment parent = comment.getParent();
            final User user = configService.getUser();
            if(comment.getAdmin()) {

            }
        }

    }
}
