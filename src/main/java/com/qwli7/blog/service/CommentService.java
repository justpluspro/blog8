package com.qwli7.blog.service;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.SavedComment;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface CommentService {


    SavedComment saveComment(Comment comment);
}
