package com.zacharye.book.dao;


import com.zacharye.book.entity.Article;
import com.zacharye.book.entity.Category;
import com.zacharye.book.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleDao {
    int addCategory(Category category);
    List<Category> findCategories(String login_user);
    int addArticle(Article article);
    int editArticle(Article article);
    List<Article> findArticlesByLoginUser(String login_user);
    List<Article> findAllSimplifiedArticles();
    List<Article> findAllSimplifiedArticlesByCondition(Map<String,Object> mapData);
    Map<String,Object> calculateTheAmountOfRecords(Map<String,Object> mapData);
    Article findArticleById(String id);
    Article findArticleByParams(Map<String,Object> mapData);
    void updateArticleInfo(String id);
    List<Article> findHotArticles();
    int addComment(Comment comment);
    List<Comment> findCommentsByArticleId(String id);
    List<Comment> findCommentsByArticleIdV2(Comment comment);
}
