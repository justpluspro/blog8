package com.qwli7.blog.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7 
 * @date 2023/2/16 17:29
 * 功能：blog8
 **/
@Entity(name = "blog_category")
@Table
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 32)
    private String name;

    @Column(name = "category_alias", nullable = false, length = 32)
    private String alias;

    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "modified_time", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
