package com.qwli7.blog.entity;

import java.io.Serializable;

public class ArticleSaved implements Serializable {
    private Integer id;

    private boolean checking;

    public ArticleSaved(Integer id, boolean checking) {
        this.id = id;
        this.checking = checking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }
}
