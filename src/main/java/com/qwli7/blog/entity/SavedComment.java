package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
public class SavedComment implements Serializable {

    public SavedComment(Integer id, Boolean checking) {
        this.id = id;
        this.checking = checking;
    }

    public SavedComment (){
        super();
    }

    private Integer id;

    private Boolean checking;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }
}
