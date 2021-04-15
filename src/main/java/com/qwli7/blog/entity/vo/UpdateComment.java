package com.qwli7.blog.entity.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改评论内容
 * @author liqiwen
 * @since 2.5
 */
public class UpdateComment implements Serializable {

    private Integer id;


    @NotBlank(message = "内容不能为空")
    @Length(message = "评论内容长度不能超过 {max} 个字符", max = 2048)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
