package com.qwli7.blog.event;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.mapper.CommentMapper;
import com.qwli7.blog.queue.DataContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * 事件处理
 * @author liqiwen
 * @since 2.3
 */
@Component
public class BlogEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final CommentMapper commentMapper;

    private final DataContainer<Comment> dataContainer;

    private BlogEventHandler(DataContainer<Comment> dataContainer, CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
        this.dataContainer = dataContainer;
    }

    @EventListener(CheckCommentEvent.class)
    public void processCheckCommentEvent(CheckCommentEvent checkCommentEvent) {
        logger.info("method<processCheckCommentEvent> source:[{}]", checkCommentEvent.getSource());
        final Comment comment = checkCommentEvent.getComment();
        // 通知评论的人，审核已经通过
        dataContainer.push(comment);

        // 通知被评论的人，您有新的评论，请注意查收
        final Comment parent = comment.getParent();
        // 如果父评论是 admin，则不通知，反之邮件通知


    }
}
