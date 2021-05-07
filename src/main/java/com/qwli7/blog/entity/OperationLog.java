package com.qwli7.blog.entity;

import org.apache.ibatis.lang.UsesJava7;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志
 * @author liqiwen
 * @since 2.4
 */
public class OperationLog extends BaseEntity implements Serializable {

    /**
     * 操作人用户名
     */
    private String username;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 操作 ip
     */
    private String ip;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime modifyAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
