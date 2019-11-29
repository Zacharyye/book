package com.zacharye.book.service;

import com.zacharye.book.dao.ArticleDao;
import com.zacharye.book.entity.Article;
import com.zacharye.book.entity.Category;
import com.zacharye.book.entity.Comment;
import com.zacharye.book.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleDao articleDao;

    /**
     * 添加分类
     * @param category
     * @return
     */

    @Override
    public Result addCategory(Category category) {
        int num = articleDao.addCategory(category);
        if(num == 0){
            return new Result().failure();
        }
        return new Result().success(category);
    }

    /**
     * 查询已创建的分类信息
     * @param username
     * @return
     */
    @Override
    public List<Category> findCategories(String username){
        return articleDao.findCategories(username);
    }

    /**
     * 编辑或添加文章
     * @param article
     * @return
     */
    @Override
    public Result editArticle(Article article) {
        Result result = null;
        int num = 0;
        if(article.getId() == null){
            //新增文章
            num = articleDao.addArticle(article);
        } else {
            //修改文章信息
            num = articleDao.editArticle(article);
        }
        if(num > 0) {
            result = new Result().success();
        } else {
            result = new Result().failure();
        }
       return result;
    }

    /**
     * 查找显示全部文章
     * @param
     * @return
     */
    @Override
    public Result findAllSimplifiedArticles(){
        List<Article> articles = articleDao.findAllSimplifiedArticles();
        return new Result().success(articles);
    }

    @Override
    public Result findAllSimplifiedArticles(Map<String, Object> mapData) {
        List<Article> articles = articleDao.findAllSimplifiedArticlesByCondition(mapData);
        Map<String,Object> result_cal = articleDao.calculateTheAmountOfRecords(mapData);
        Map<String,Object> result = new HashMap<String, Object>();
        List<Article> hot_articles = articleDao.findHotArticles();
        result.put("articles",articles);
        result.put("r_cal",result_cal);
        result.put("hot_articles",hot_articles);
        return new Result().success(result);
    }

    /**
     * 查找文章：根据指定id
     * @param id
     * @return
     */
    @Override
    public Article findArticleById (String id) {
        articleDao.updateArticleInfo(id);
        return articleDao.findArticleById(id);
    }

    /**
     * 根据给定参数查找文章
     * @param mapData
     * @return
     */
    @Override
    public Article findArticleByParams(Map<String, Object> mapData) {
        return null;
    }

    /**
     * 查询热门文章
     * @return
     */
    @Override
    public List<Article> findHotArticles() {
        return articleDao.findHotArticles();
    }

    /**
     * 添加评论
     * @param dataMap
     * @return
     */
    @Override
    public Result addComment(Map<String, Object> dataMap) {
        Result result = null;
        String author = dataMap.get("commentAuthor") + "";
        String email = dataMap.get("commentEmail") + "";
        String article_id = dataMap.get("articleId") + "";
        String content = dataMap.get("commentContent") + "";
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setEmail(email);
        comment.setArticle_id(Integer.valueOf(article_id));
        comment.setContent(content);
        int num = articleDao.addComment(comment);
        if(num > 0){
            result = new Result().success();
        } else {
            result = new Result().failure();
        }
        return result;
    }

    /**
     * 根据文章id查询其所有评论
     * @param id
     * @return
     */
    @Override
    public List<Comment> findCommentsByArticleId(String id) {
        return articleDao.findCommentsByArticleId(id);
    }


    /**
     * 查询用户评论
     * @param comment
     * @return
     */
    @Override
    public Result findCommentsByArticleIdV2(Comment comment) {
        List<Comment> comments = articleDao.findCommentsByArticleIdV2(comment);
        return new Result().success(comments);
    }

}
