package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.enums.ArticleState;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 16:57
 * 功能：blog8
 **/
public class ArticleBean implements Serializable {

    @NotBlank(message = "标题不能为空")
    @Length(max = 128, min = 1, message = "标题长度{min}到{max}之间")
    private String title;

    @NotBlank(message = "内容不能为空")
    @Length(max = 10240, min = 1, message = "内容长度{min}到{max}之间")
    private String content;

    @NotBlank(message = "分类id不能为空")
    private Integer categoryId;

    @NotNull(message = "状态不能为空")
    private ArticleState articleState;

    /**
     * 别名
     */
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public ArticleState getArticleState() {
        return articleState;
    }

    public void setArticleState(ArticleState articleState) {
        this.articleState = articleState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
