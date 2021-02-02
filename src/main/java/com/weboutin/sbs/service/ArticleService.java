package com.weboutin.sbs.service;

import java.util.Date;
import java.util.List;

import com.weboutin.sbs.entity.Article;
import com.weboutin.sbs.mappers.ArticleMapper;
import com.weboutin.sbs.utils.MyBatisUtils;

import org.apache.ibatis.session.SqlSession;

public class ArticleService {

    public static Integer create(Integer userId, String title, String content) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Date date = new Date();
        Article article = new Article();
        article.userId = userId;
        article.title = title;
        article.content = content;
        article.createdAt = date.getTime();
        article.modifiedAt = date.getTime();
        mapper.insertArticle(article);
        return article.articleId;
    }

    public static List<Article> getAll(int page, int size) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        List<Article> articles = mapper.getArticles(page, size);
        return articles;
    }

    public static Article getDetail(int articleId) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Article article = mapper.getArticleById(articleId);
        return article;
    }

    public static void edit(Integer userId, Integer articleId, String title, String content) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Date date = new Date();
        Article article = new Article();
        article.articleId = articleId;
        article.userId = userId;
        article.title = title;
        article.content = content;
        article.modifiedAt = date.getTime();
        Integer effectRow = mapper.updateArticle(article);
        if (effectRow == 0) {
            throw new Exception("Update error");
        }
    }

    public static void remove(Integer userId, Integer articleId) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Article article = new Article();
        article.userId = userId;
        article.articleId = articleId;
        Integer effectRow = mapper.removeArticleById(article);
        if (effectRow == 0) {
            throw new Exception("Remove error");
        }
    }
}