package com.weboutin.sbs.service;

import java.util.Date;
import java.util.List;
import com.weboutin.sbs.entity.Article;
import com.weboutin.sbs.mappers.ArticleMapper;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private ArticleMapper articleMapper;

    public Integer create(Integer userId, String title, String content) throws Exception {
        Date date = new Date();
        Article article = new Article();
        article.userId = userId;
        article.title = title;
        article.content = content;
        article.createdAt = date.getTime();
        article.modifiedAt = date.getTime();
        articleMapper.insertArticle(article);
        return article.articleId;
    }

    public List<Article> getAll(int page, int size) throws Exception {
        String result = new String(jedisConnectionFactory.getConnection().get("hello".getBytes()));
        System.out.println(result);


        List<Article> articles = articleMapper.getArticles(page, size);
        return articles;
    }

    public Article getDetail(int articleId) throws Exception {
        Article article = articleMapper.getArticleById(articleId);
        return article;
    }

    public void edit(Integer userId, Integer articleId, String title, String content) throws Exception {
        Date date = new Date();
        Article article = new Article();
        article.articleId = articleId;
        article.userId = userId;
        article.title = title;
        article.content = content;
        article.modifiedAt = date.getTime();
        Integer effectRow = articleMapper.updateArticle(article);
        if (effectRow == 0) {
            throw new Exception("Update error");
        }
    }

    public void remove(Integer userId, Integer articleId) throws Exception {
        Article article = new Article();
        article.userId = userId;
        article.articleId = articleId;
        Integer effectRow = articleMapper.removeArticleById(article);
        if (effectRow == 0) {
            throw new Exception("Remove error");
        }
    }
}