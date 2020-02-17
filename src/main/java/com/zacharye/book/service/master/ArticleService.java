package com.zacharye.book.service.master;

import com.zacharye.book.entity.master.Article;
import com.zacharye.book.entity.master.Category;
import com.zacharye.book.entity.master.Comment;
import com.zacharye.book.util.Result;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Result addCategory(Category category);
    List<Category> findCategories(String username);
    Result editArticle(Article article);
    Result findAllSimplifiedArticles();
    Result findAllSimplifiedArticles(Map<String,Object> mapData);
    Article findArticleById(String id);
    Article findArticleByParams(Map<String,Object> mapData);
    List<Article> findHotArticles();
    Result addComment(Map<String,Object> dataMap);
    List<Comment> findCommentsByArticleId(String id);
    Result findCommentsByArticleIdV2(Comment comment);
}
