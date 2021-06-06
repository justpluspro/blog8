package com.qwli7.blog.event;

import com.qwli7.blog.entity.*;
import com.qwli7.blog.mapper.ArticleMapper;
import com.qwli7.blog.mapper.CommentMapper;
import com.qwli7.blog.mapper.MomentMapper;
import com.qwli7.blog.queue.DataContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Optional;


/**
 * 事件处理
 * @author liqiwen
 * @since 2.3
 */
@Component
public class BlogEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final CommentMapper commentMapper;

    private final ArticleMapper articleMapper;

    private final MomentMapper momentMapper;

    private final SpringTemplateEngine templateEngine;

    private final DataContainer<Comment> dataContainer;

    private BlogEventHandler(DataContainer<Comment> dataContainer, CommentMapper commentMapper,
                             ArticleMapper articleMapper, MomentMapper momentMapper,
                             SpringTemplateEngine templateEngine) {
        this.commentMapper = commentMapper;
        this.dataContainer = dataContainer;
        this.articleMapper = articleMapper;
        this.momentMapper = momentMapper;
        this.templateEngine = templateEngine;
    }

    /**
     * 评论发布事件
     * @param commentPostEvent 评论发布事件
     */
    @EventListener(CommentPostEvent.class)
    public void processCommentPostEvent(CommentPostEvent commentPostEvent) {
        logger.info("method<processCommentPostEvent> source:[{}]", commentPostEvent.getSource());
        // 非管理员邮件
        final Comment comment = commentPostEvent.getComment();
        final CommentStatus status = comment.getStatus();
        if(CommentStatus.CHECKING == status) {
            // 推送审核邮件,未审核时，不需要给动态或者文章的评论数量 + 1
        } else {
            // 评论状态正常
            final CommentModule module = comment.getModule();
            if (module.getName().equals(Moment.class.getSimpleName().toLowerCase())) {
                final Optional<Moment> momentOptional = momentMapper.findById(module.getId());
                if(momentOptional.isPresent()) {
                    final Moment moment = momentOptional.get();
                    momentMapper.addComments(moment.getId(), 1);
                }
                return;
            }
            if(module.getName().equals(Article.class.getSimpleName().toLowerCase())) {
                final Optional<Article> articleOp = articleMapper.findById(module.getId());
                if(articleOp.isPresent())  {
                    final Article article = articleOp.get();
                    articleMapper.addComments(article.getId(), 1);
                }
                return;
            }
            final Comment parent = comment.getParent();
            if(parent != null) {
                if(parent.getAdmin()) {
                    // 向管理员推送回复邮件
                    IContext context = new Context();

                    templateEngine.process("", context);
                } else {
                    // 向被回复者推送被回复邮件
                }
            }  else {
                // 向管理员推送评论邮件
            }
        }
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
