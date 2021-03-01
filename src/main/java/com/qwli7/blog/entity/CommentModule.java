package com.qwli7.blog.entity;

import java.io.Serializable;

public class CommentModule implements Serializable {
    private Integer id;

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
