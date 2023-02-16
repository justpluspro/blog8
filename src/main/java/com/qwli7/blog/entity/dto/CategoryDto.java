package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Category;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 17:33
 * 功能：blog8
 **/
public class CategoryDto implements Serializable {

    private Integer id;

    private String categoryName;

    private String categoryAlias;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.categoryAlias = category.getAlias();
        this.categoryName = category.getName();
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
}
