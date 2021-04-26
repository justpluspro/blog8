package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * 模板查询参数
 * @author liqiwen
 * @since 2.0
 */
public class TemplateQueryParam implements Serializable {
    /**
     * 模板是否激活
     */
    private Boolean enable;

    /**
     * 模板名称
     */
    private String name;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
