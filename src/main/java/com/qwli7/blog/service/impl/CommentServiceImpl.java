package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.CommentStrategy;
import com.qwli7.blog.entity.*;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.CommentMapper;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.CommentService;
import com.qwli7.blog.service.ConfigService;
import javafx.beans.property.ObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
 * 功能：blog8
 **/
@Service
public class CommentServiceImpl implements CommentService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final static int MAX_PARENT_PATH_DEPTH = 255;
    private final static String DEFAULT_PARENT_PATH = "/";

    private final CommentMapper commentMapper;
    private final ConfigService configService;

    private final List<CommentModuleHandler> moduleHandlers;

    public CommentServiceImpl(CommentMapper commentMapper, ConfigService configService,
                              ObjectProvider<CommentModuleHandler> objectProvider) {
        this.commentMapper = commentMapper;
        this.configService = configService;
        this.moduleHandlers = objectProvider.stream().collect(Collectors.toList());
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SavedComment saveComment(Comment comment) {
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
        if(parent != null) {

            final Optional<Comment> parentOp = commentMapper.selectById(parent.getId());
            if(!parentOp.isPresent()) {
                throw new LogicException("parent.notExists", "父评论不存在");
            }
            parent = parentOp.get();

            final CommentStatus status = parent.getStatus();
            if(status == null || status == CommentStatus.CHECKING) {
                throw new LogicException("parent.checking", "父评论正在审核中");
            }
            parentPath = parent.getParentPath() + parent.getId() + parentPath;
            if(parentPath.length() > MAX_PARENT_PATH_DEPTH) {
                throw new LogicException("parentPath.too.depth", "评论不允许回复了");
            }
        }
        comment.setParentPath(parentPath);

        final String ip = comment.getIp();
        boolean checking = false;

        if(BlogContext.isAuthenticated()) {
            comment.setName("");
            comment.setAdmin(true);
            final User user = configService.getUser();
            comment.setEmail(user.getEmail());
            comment.setAvatar(user.getAvatar());
            comment.setStatus(CommentStatus.NORMAL);
        } else {
            final String email = comment.getEmail();
            if (StringUtils.isEmpty(email)) {
                final BlogConfig config = configService.getConfig();
                comment.setAvatar(config.getDefaultAvatar());
            } else {
                final String partAvatar = DigestUtils.md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8));
                comment.setAvatar(partAvatar);
            }

            final CommentStrategy commentStrategy = configService.getCommentStrategy();
            switch (commentStrategy) {
                case EACH:
                    checking = true;
                    break;
                case FIRST:

                    break;
                case NEVER:
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
                final Comment _parent = comment.getParent();
                //email notify

            }
        });
        return new SavedComment(comment.getId(), checking);

    }
}
