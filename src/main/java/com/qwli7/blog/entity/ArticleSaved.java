package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * 保存后的文章
 * @author liqiwen
 * @since 1.2
 */
public class ArticleSaved implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 是否在审核中
     */
    private boolean checking;

    public ArticleSaved(Integer id, boolean checking) {
        super();
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
