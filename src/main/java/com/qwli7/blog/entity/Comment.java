package com.qwli7.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7
 * @date 2021/2/22 13:05
 * 功能：blog8
 **/
public class Comment extends BaseEntity implements Serializable {


    private String name;

    private String email;

    private String content;

    private String ip;

    private Comment parent;

    /**
     * 显示会话深度，最多不能超过 255
     */
    private String parentPath;


    private CommentStatus status;

    private LocalDateTime createAt;

    private LocalDateTime modifyAt;


    private Boolean admin;

    private Boolean checking;


    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private CommentModule module;

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public CommentModule getModule() {
        return module;
    }

    public void setModule(CommentModule module) {
        this.module = module;
    }
}
