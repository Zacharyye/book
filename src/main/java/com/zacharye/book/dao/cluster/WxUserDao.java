package com.zacharye.book.dao.cluster;

import com.zacharye.book.entity.cluster.Book;
import com.zacharye.book.entity.cluster.Comment;
import com.zacharye.book.entity.cluster.WxUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WxUserDao {
    WxUser findUserByOpenId(String openid);
    int addWxUser(WxUser user);
    List<Book> findAllBooks();
    List<Comment> findCommentsByBookId(String bkid);
    String findBookIsBuy(Map<String,Object> data);
    int addComment(Map<String, Object> data);
    int addOrderInfo (Map<String, Object> data);
    int updateUserInfo (Map<String, Object> data);
    List<Book> getMyBooks(Map<String,Object> data);
}
