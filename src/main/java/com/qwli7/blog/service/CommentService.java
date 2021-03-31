package com.qwli7.blog.service;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.SavedComment;
import com.qwli7.blog.entity.dto.CommentDto;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommentQueryParam;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：CommentService
 **/
public interface CommentService {

    /**
     * 发布评论
     * @param comment comment
     * @return SavedComment
     */
    SavedComment saveComment(Comment comment);

    /**
     * 分页查找评论
     * @param commentQueryParam 评论查询参数
     * @return PageDto
     */
    PageDto<CommentDto> selectPage(CommentQueryParam commentQueryParam);

    /**
     * 删除评论
     * @param comment comment
     */
    void delete(Comment comment);
}
