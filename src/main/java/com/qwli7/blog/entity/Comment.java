package com.qwli7.blog.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7
 * @date 2021/2/22 13:05
 * 功能：blog8
 **/
public class Comment extends BaseEntity implements Serializable {


    @NotBlank(message = "名称不能为空")
    @Length(max = 16, message = "名称长度不能超过{max}")
    private String name;

    @Email(message = "请输入合法的邮箱地址")
    private String email;

    @NotBlank(message = "内容不能为空")
    @Length(max = 1024, message = "评论长度不能超过{max}")
    private String content;

    /**
     * 评论 ip
     */
    private String ip;

    /**
     * 父评论
     */
    private Comment parent;

    /**
     * 显示会话深度，最多不能超过 255
     */
    private String conversationPath;

    /**
     * 评论状态
     */
    private CommentStatus status;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime modifyAt;

    /**
     * 是否是管理员评论
     */
    private Boolean admin;

    /**
     * 评论是否在审核中
     */
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

    public String getConversationPath() {
        return conversationPath;
    }

    public void setConversationPath(String conversationPath) {
        this.conversationPath = conversationPath;
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
