package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;

/**
 * 文章索引
 * 利用 lucene 构建文章类型全局索引
 * @author liqiwen
 * @since 2.0
 */
public class ArticleIndexer {

    /**
     * ID
     */
    private static final String ID = "id";

    /**
     * title
     */
    private static final String TITLE = "title";

    /**
     * content
     */
    private static final String CONTENT = "content";

    /**
     * summary
     */
    private static final String SUMMARY = "summary";

    /**
     * alias
     */
    private static final String ALIAS = "alias";

    /**
     * 分类 id
     */
    private static final String CATEGORY_ID = "category_id";

    /**
     * 标签名称
     */
    private static final String TAG_NAME = "tag_name";

    /**
     * 发布时间
     */
    private static final String POST_AT = "post_at";


    private static Directory createDirectory() {


//        return new DIr
        return null;
    }


    /**
     * 添加索引
     * @param article article
     */
    private void addDocument(Article article) {
        Document document = new Document();
//        new TextField(ID, )


    }

}
