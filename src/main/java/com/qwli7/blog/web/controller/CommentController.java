package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.CommentModule;
import com.qwli7.blog.entity.SavedComment;
import com.qwli7.blog.entity.dto.CommentDto;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommentQueryParam;
import com.qwli7.blog.entity.vo.UpdateComment;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：CommentController
 **/
@RestController
@RequestMapping("api")
public class CommentController {


    private final CommentService commentService;
    private final BlogProperties blogProperties;

    public CommentController(CommentService commentService, BlogProperties blogProperties) {
        this.commentService = commentService;
        this.blogProperties = blogProperties;
    }

    /**
     * 保存评论
     * @param comment comment
     * @param module module
     * @param id id
     * @return SavedComment
     */
    @PostMapping("{module}/{id}/comment")
    public SavedComment save(@RequestBody Comment comment, @PathVariable("module") String module,
                             @PathVariable("id") int id) {
        final CommentModule commentModule = new CommentModule(id, module);
        comment.setModule(commentModule);
        comment.setIp("127.0.2.1");
        return commentService.saveComment(comment);
    }

    /**
     * 获取某个模块某个内容的评论列表
     * @param commentQueryParam commentQueryParam
     * @param module module
     * @param id id
     * @return PageDto
     */
    @GetMapping("{module}/{id}/comments")
    public PageDto<CommentDto> selectPage(CommentQueryParam commentQueryParam,
                                          @PathVariable("module") String module,
                                          @PathVariable("id") int id) {
        final CommentModule commentModule = new CommentModule(id, module);
        commentQueryParam.setCommentModule(commentModule);
        if (commentQueryParam.hasNoSize()) {
            commentQueryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return commentService.findPage(commentQueryParam);
    }

    /**
     * 删除某个评论
     * @param id id
     * @return ResponseEntity
     */
    @Authenticated
    @DeleteMapping("comment/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        final Comment comment = new Comment();
        comment.setId(id);
        commentService.delete(comment);
        return ResponseEntity.noContent().build();
    }

    /**
     * 审核评论
     * @param id id
     * @return ResponseEntity
     */
    @Authenticated
    @PutMapping("comment/{id}/check")
    public ResponseEntity<SavedComment> checkComment(@PathVariable("id") int id) {
        return ResponseEntity.ok(commentService.check(id));
    }

    /**
     * 修改评论, 仅管理员对自己的评论有效
     * @param id id
     * @param updateComment updateComment
     * @return ResponseEntity
     */
    @Authenticated
    @PutMapping("comment/{id}/update")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody @Valid UpdateComment updateComment) {
        updateComment.setId(id);
        commentService.update(updateComment);
        return ResponseEntity.noContent().build();
    }
}
