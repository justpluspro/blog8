package com.qwli7.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7
 * @date 2023/2/16 17:29
 * 功能：blog8
 **/
public class Category implements Serializable {

    private Integer id;

    private String categoryName;

    private String categoryAlias;

    //私人空间
    private Boolean privateSpace;

    //默认空间
    private Boolean defaultSpace;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public Boolean getDefaultSpace() {
        return defaultSpace;
    }

    public void setDefaultSpace(Boolean defaultSpace) {
        this.defaultSpace = defaultSpace;
    }

    public Boolean getPrivateSpace() {
        return privateSpace;
    }

    public void setPrivateSpace(Boolean privateSpace) {
        this.privateSpace = privateSpace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
