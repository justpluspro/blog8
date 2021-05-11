package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.CommentStrategy;
import com.qwli7.blog.entity.*;
import com.qwli7.blog.entity.dto.CommentDto;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommentQueryParam;
import com.qwli7.blog.entity.vo.UpdateComment;
import com.qwli7.blog.event.CheckCommentEvent;
import com.qwli7.blog.event.CommentPostEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.CommentMapper;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.CommentService;
import com.qwli7.blog.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：CommentServiceImpl
 **/
@Service
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final static int MAX_PARENT_PATH_DEPTH = 255;
    private final static String DEFAULT_PARENT_PATH = "/";

    private final CommentMapper commentMapper;
    private final ConfigService configService;
    private final DataContainer<Comment> dataContainer;
    private final ApplicationEventPublisher publisher;

    private final List<CommentModuleHandler> moduleHandlers;

    public CommentServiceImpl(CommentMapper commentMapper, ConfigService configService,
                              DataContainer<Comment> dataContainer,
                              ApplicationEventPublisher publisher,
                              ObjectProvider<CommentModuleHandler> objectProvider) {
        this.commentMapper = commentMapper;
        this.configService = configService;
        this.dataContainer = dataContainer;
        this.publisher = publisher;
        this.moduleHandlers = objectProvider.stream().collect(Collectors.toList());
    }

    /**
     * 待保存的评论
     * @param comment comment
     * @return commentSaved
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SavedComment saveComment(Comment comment) {
        logger.info("method<saveComment> comment:[{}]", comment.toString());
        final CommentModule module = comment.getModule();
        if(moduleHandlers.isEmpty()) {
            throw new LogicException("module.notExists", "模块不存在");
        }

        final Optional<CommentModuleHandler> commentModuleHandlerOp = moduleHandlers.stream().filter(e ->
            e.getModuleName().equals(module.getName())
        ).findAny();

        if(!commentModuleHandlerOp.isPresent()) {
            throw new LogicException("module.notExists", "模块[" + module.getName() + "]不存在");
        }

        final CommentModuleHandler commentModuleHandler = commentModuleHandlerOp.get();
        commentModuleHandler.validateBeforeInsert(module);

        comment.setCreateAt(LocalDateTime.now());
        comment.setModifyAt(LocalDateTime.now());

        String parentPath = DEFAULT_PARENT_PATH;
        Comment parent = comment.getParent();

        // 父评论不为空
        if(parent != null) {
            // 判断父评论是否存在
            final Optional<Comment> parentOp = commentMapper.selectById(parent.getId());
            if(!parentOp.isPresent()) {
                throw new LogicException("parent.notExists", "父评论不存在");
            }
            parent = parentOp.get();

            // 判断父评论状态
            final CommentStatus status = parent.getStatus();
            if(status == null || status == CommentStatus.CHECKING) {
                throw new LogicException("parent.checking", "父评论正在审核中");
            }
            parentPath = parent.getConversationPath() + parent.getId() + parentPath;

            // 判断回复的深度
            if(parentPath.length() > MAX_PARENT_PATH_DEPTH) {
                throw new LogicException("parentPath.too.depth", "评论不允许回复了");
            }
        }
        comment.setConversationPath(parentPath);

        final String ip = comment.getIp();
        boolean checking = false;

        // 如果用户在已登录的情况下
        if(BlogContext.isAuthenticated()) {
            // 不设置用户名称
            comment.setName("");
            comment.setAdmin(true);
            final User user = configService.getUser();
            comment.setEmail(user.getEmail());
            comment.setAvatar(user.getAvatar());
            comment.setStatus(CommentStatus.NORMAL);
            comment.setChecking(false);
        } else {
            // 用户未登录
            final String email = comment.getEmail();
            if (StringUtils.isEmpty(email)) {
                // 如果邮箱为空，则设置默认的头像
                final BlogConfig config = configService.getConfig();
                comment.setAvatar(config.getDefaultAvatar());
            } else {
                // 如果用户邮箱不为空，则根据邮箱计算头像的 md5
                final String partAvatar = DigestUtils.md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8));
                comment.setAvatar(partAvatar);
            }
            comment.setAdmin(false);

            // 判断审核策略
            final CommentStrategy commentStrategy = configService.getCommentStrategy();
            switch (commentStrategy) {
                case EACH: //每次评论都需要审核
                    checking = true;
                    break;
                case FIRST: //第一次评论需要审核
                    Optional<Comment> commentOp = commentMapper.selectLatestCommentByIp(ip);
                    if(commentOp.isPresent()) {
                        // 如果存在，则看最近一条的评论是什么状态
                        final Comment ipComment = commentOp.get();
                        final CommentStatus status = ipComment.getStatus();

                        // 如果最近的一条评论也是审核中，则该条评论直接设置成审核中
                        // 反之则不审核
                        checking = CommentStatus.CHECKING == status;
                    } else {
                        checking = true;
                    }
                    break;
                case NEVER: //从来不要审核
                    checking = false;
                    break;
                default:
                    throw new LogicException("invalid.strategy", "无效的评论策略");
            }
            comment.setChecking(checking);
            comment.setStatus(checking ? CommentStatus.CHECKING : CommentStatus.NORMAL);
        }

        commentMapper.insert(comment);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                final Boolean admin = comment.getAdmin();
                //如果此条评论是管理员评论，则不推送邮件
                if(!admin) {
                    // 非管理员评论，向管理员推送评论 || 回复邮件
                    publisher.publishEvent(new CommentPostEvent(this, comment));
                }
            }
        });
        return new SavedComment(comment.getId(), checking);

    }

    /**
     * 分页查询评论
     * @param commentQueryParam 评论查询参数
     * @return PageDto
     */
    @Transactional(readOnly = true)
    @Override
    public PageDto<CommentDto> selectPage(CommentQueryParam commentQueryParam) {
        final CommentModule commentModule = commentQueryParam.getCommentModule();
        final Optional<CommentModuleHandler> commentModuleHandlerOp = moduleHandlers.stream().filter(e ->
                e.getModuleName().equals(commentModule.getName())
        ).findAny();

        if(!commentModuleHandlerOp.isPresent()) {
            throw new LogicException("module.notExists", "模块[" + commentModule.getName() + "]不存在");
        }
        commentModuleHandlerOp.get().validateBeforeQuery(commentModule);
        if(BlogContext.isAuthenticated()) {

        }

        long count = commentMapper.count(commentQueryParam);
        if(count == 0) {
            return new PageDto<>(commentQueryParam, 0, new ArrayList<>());
        }
        List<Comment> comments = commentMapper.selectPage(commentQueryParam);
        if(CollectionUtils.isEmpty(comments)) {
            return new PageDto<>(commentQueryParam, 0, new ArrayList<>());
        }
        final List<CommentDto> commentDtos = comments.stream().map(CommentDto::new).collect(Collectors.toList());

        return new PageDto<>(commentQueryParam, ((int) count), commentDtos);
    }

    /**
     * 删除评论
     * @param comment comment
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(Comment comment) {
        final Optional<Comment> commentOp = commentMapper.selectById(comment.getId());
        if(!commentOp.isPresent()) {
            throw new LogicException("comment.notExists", "评论不存在");
        }
        // 删除评论下的子评论
        commentMapper.deleteById(commentOp.get().getId());
        dataContainer.remove(comment);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(UpdateComment updateComment) {
        final Comment comment = commentMapper.selectById(updateComment.getId()).orElseThrow(() ->
                new LogicException("comment.notExists", "评论不存在"));
        final Boolean admin = comment.getAdmin();
        if(!admin) {
            throw new LogicException("illegal.operation", "管理员仅仅只能修改自己的评论");
        }
        // 内容相同
        if(comment.getContent().equals(updateComment.getContent())) {
            return;
        }

        Comment update = new Comment();
        comment.setId(updateComment.getId());
        comment.setContent(updateComment.getContent());
        comment.setModifyAt(LocalDateTime.now());

        commentMapper.update(update);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SavedComment check(int id) {
        final Comment comment = commentMapper.selectById(id).orElseThrow(()
                -> new LogicException("comment.notExists", "评论不存在"));
        final CommentStatus status = comment.getStatus();
        final Boolean checking = comment.getChecking();
        if(status != CommentStatus.CHECKING || !checking) {
            throw new LogicException("invalid.operation", "无效的审核操作");
        }
        final Comment updateComment = new Comment();
        updateComment.setId(comment.getId());
        updateComment.setChecking(false);
        updateComment.setStatus(CommentStatus.NORMAL);
        updateComment.setModifyAt(LocalDateTime.now());
        commentMapper.update(updateComment);


        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                publisher.publishEvent(new CheckCommentEvent(this, comment));
            }
        });

        return new SavedComment(comment.getId(), true);
    }
}
