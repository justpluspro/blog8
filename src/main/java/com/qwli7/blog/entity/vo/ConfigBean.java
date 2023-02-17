package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/2/17 17:42
 * 功能：blog8
 **/
public class ConfigBean implements Serializable {

    private String avatarUrl;


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
