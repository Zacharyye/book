package com.zacharye.book.service.cluster;

import com.zacharye.book.entity.cluster.WxUser;
import com.zacharye.book.util.Result;
import org.springframework.stereotype.Service;
import java.util.Map;

public interface WxUserService {
    Result<WxUser> findUserByOpenId(Map<String,Object> data);
    Result findBooks(Map<String,Object> data);
    Result findBookCommentById (Map<String, Object> data);
    Result addComment(Map<String, Object> data);
    Result buyBook(Map<String,Object> data);
    Result getMyBooks(Map<String, Object> data);
}
