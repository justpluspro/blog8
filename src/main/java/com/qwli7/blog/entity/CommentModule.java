package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author liqiwen
 * 评论模块
 */
public class CommentModule implements Serializable {
    /**
     * 模块 id
     */
    private Integer id;

    /**
     * 模块名称
     */
    private String name;

    public CommentModule() {
        super();
    }


    public CommentModule(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
