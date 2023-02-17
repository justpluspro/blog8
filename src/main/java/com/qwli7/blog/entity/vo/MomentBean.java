package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:54
 * 功能：blog8
 **/
public class MomentBean implements Serializable {

    private String content;

    private Boolean allowComment;

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
