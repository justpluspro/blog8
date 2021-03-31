package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;

/**
 * 文章索引
 * @author liqiwen
 * @since 2.0
 */
public class ArticleIndexer {


    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String SUMMARY = "summary";
    private static final String ALIAS = "alias";
    private static final String CATEGORY_ID = "catgory_id";
    private static final String TAG_NAME = "tag_name";


    private static Directory createDirectory() {


//        return new DIr
        return null;
    }


    /**
     * 添加索引
     * @param article
     */
    private void addDocument(Article article) {
        Document document = new Document();
//        new TextField(ID, )


    }

}
