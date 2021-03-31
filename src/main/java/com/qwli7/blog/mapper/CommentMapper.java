package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.CommentModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：CommentMapper
 **/
@Mapper
public interface CommentMapper {

    /**
     * 根据模块删除评论
     * @param commentModule commentModule
     */
    void deleteByModule(CommentModule commentModule);

    /**
     * 根据 id 查询评论
     * @param id id
     * @return Comment
     */
    Optional<Comment> selectById(Integer id);

    /**
     * 插入评论
     * @param comment comment
     */
    void insert(Comment comment);

    /**
     * 获取 ip 的最近一条评论
     * @param ip ip
     * @return Comment
     */
    Optional<Comment> selectLatestCommentByIp(String ip);

    /**
     * 删除评论根据 id
     * @param id id
     */
    void deleteById(Integer id);
}
