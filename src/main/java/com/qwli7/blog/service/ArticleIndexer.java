package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleStatus;
import com.qwli7.blog.entity.vo.HandledArticleQueryParam;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    private final Path directoryPath = Paths.get(System.getProperty("user.home")).resolve("blog/index");

    /**
     * 写索引工具
     */
    private final IndexWriter indexWriter;

    /**
     * 索引存放目录
     */
    private final Directory directory;

    /**
     * 分词器
     */
    private final Analyzer analyzer;

    public ArticleIndexer() throws IOException {
        this.directory = createDirectory();
        this.analyzer = createAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
    }



    private Directory createDirectory() throws IOException {
        return FSDirectory.open(directoryPath);
    }

    private Analyzer createAnalyzer() {
        return new SmartChineseAnalyzer();
    }


    public void addIndex(Article article) throws IOException{
        final Document document = createDocument(article);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 根据索引搜索
     * @param queryParam queryParam
     * @return List<Integer>
     * @throws IOException IOException
     * @throws ParseException ParseException
     */
    public List<Integer> doSearch(HandledArticleQueryParam queryParam) throws IOException, ParseException {
        final String query = queryParam.getQuery();
        final List<ArticleStatus> statuses = queryParam.getStatuses();

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        Analyzer analyzer = new StandardAnalyzer();

        QueryParser queryParser = new QueryParser(TITLE, analyzer);
        final Query parse = queryParser.parse(query);


        final TopDocs topDocs = indexSearcher.search(parse, 10);
        final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Integer> ids = new ArrayList<>();
        for(ScoreDoc scoreDoc: scoreDocs) {
            final Document doc = indexSearcher.doc(scoreDoc.doc);
            ids.add((Integer.parseInt(doc.get(ID))));
        }
        return ids;
    }

    /**
     * 添加索引
     * TextField 不分词
     * StringField 分词
     * @param article article
     */
    private Document createDocument(Article article) {
        Document document = new Document();
        document.add(new TextField(ID, String.valueOf(article.getId()), Field.Store.YES));
        document.add(new StringField(TITLE, article.getTitle(), Field.Store.YES));
        document.add(new StringField(CONTENT, article.getContent(), Field.Store.NO));
        if(!StringUtils.isEmpty(article.getAlias())) {
            document.add(new TextField(ALIAS, article.getAlias(), Field.Store.YES));
        }

        if(!StringUtils.isEmpty(article.getSummary())) {
            document.add(new StringField(SUMMARY, article.getSummary(), Field.Store.NO));
        }
        return document;
    }


}
