package com.qwli7.blog.service;


import com.qwli7.blog.entity.CommentModule;

/**
 * 评论模块处理
 * @author liqiwen
 * @since 1.2
 */
public interface CommentModuleHandler {

    /**
     * 获取模块名称
     * @return String
     */
    String getModuleName();

    /**
     * 插入之前校验
     * @param module module
     */
    void validateBeforeInsert(CommentModule module);

    /**
     * 查询之前校验
     * @param module module
     */
    void validateBeforeQuery(CommentModule module);
}
