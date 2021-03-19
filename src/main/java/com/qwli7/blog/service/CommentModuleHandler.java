package com.qwli7.blog.service;


import com.qwli7.blog.entity.CommentModule;

public interface CommentModuleHandler {

    String getModuleName();

    void validateBeforeInsert(CommentModule module);

    void validateBeforeQuery(CommentModule module);
}
