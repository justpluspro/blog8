package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.CommentModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:49
 * 功能：blog8
 **/
@Mapper
public interface CommentMapper {
    void deleteByModule(CommentModule commentModule);

    Optional<Comment> selectById(Integer id);

    void insert(Comment comment);

    Optional<Comment> selectLatestCommentByIp(String ip);

    void deleteById(Integer id);
}
