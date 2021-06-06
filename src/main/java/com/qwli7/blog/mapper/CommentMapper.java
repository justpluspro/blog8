package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.CommentModule;
import com.qwli7.blog.entity.vo.CommentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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
    Optional<Comment> findById(Integer id);

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
    Optional<Comment> findLatestCommentByIp(String ip);

    /**
     * 删除评论根据 id
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 修改评论
     * @param comment comment
     */
    void update(Comment comment);

    /**
     * 获取评论数量
     * @param commentQueryParam commentQueryParam
     * @return count
     */
    long count(CommentQueryParam commentQueryParam);

    /**
     * 分页获取评论
     * @param commentQueryParam commentQueryParam
     * @return List
     */
    List<Comment> findPage(CommentQueryParam commentQueryParam);
}
