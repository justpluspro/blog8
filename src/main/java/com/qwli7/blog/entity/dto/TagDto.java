package com.qwli7.blog.entity.dto;

import com.qwli7.blog.dao.TagDao;
import com.qwli7.blog.entity.Tag;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/20 15:32
 * 功能：blog8
 **/
public class TagDto implements Serializable {

    private Integer tagId;

    private String tagName;

    private String tagAlias;


    public TagDto(Tag tag) {
        this.tagId = tag.getId();
        this.tagName = tag.getName();
        this.tagAlias = tag.getAlias();
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagAlias() {
        return tagAlias;
    }

    public void setTagAlias(String tagAlias) {
        this.tagAlias = tagAlias;
    }
}
