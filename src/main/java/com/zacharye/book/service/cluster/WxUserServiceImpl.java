package com.zacharye.book.service.cluster;

import com.alibaba.fastjson.JSONObject;
import com.zacharye.book.dao.cluster.WxUserDao;
import com.zacharye.book.entity.cluster.Book;
import com.zacharye.book.entity.cluster.Comment;
import com.zacharye.book.entity.cluster.WxUser;
import com.zacharye.book.util.EncryptUtil;
import com.zacharye.book.util.RestHttpUtils;
import com.zacharye.book.util.Result;
import com.zacharye.book.util.WxAppConfig;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wxUserService")
public class WxUserServiceImpl implements WxUserService {

    @Resource
    private WxAppConfig wxAppConfig;

    @Resource
    private WxUserDao wxUserDao;

    @Override
    public Result<WxUser> findUserByOpenId(Map<String, Object> data) {
        Result<WxUser> result = new Result<>();
        JSONObject respmsg = null;
        String code = data.get("code") == null ? "" : data.get("code") + "";
        String rawData = data.get("rawData") == null ? "" : data.get("rawData") + "";
        JSONObject rawObj = JSONObject.parseObject(rawData);
        WxUser respUser = null;
        if ("".equals(code)) {
            return result.failure();
        }
        try {
            respmsg = RestHttpUtils.httpGet( "https://api.weixin.qq.com/sns/jscode2session?appid="
                    + wxAppConfig.getAppId() + "&secret="
                    + wxAppConfig.getSecret() + "&js_code="
                    + code
                    + "&grant_type=authorization_code");

            String openId = respmsg.getString("openid");
            String session_key = respmsg.getString("session_key");
            String skey = EncryptUtil.encryptDataWithSha1(session_key);
            respUser = wxUserDao.findUserByOpenId(openId);
            if (respUser == null) {
                //add wxuser
                WxUser addUser = new WxUser();
                addUser.setSessionkey(session_key);
                addUser.setSkey(skey);
                addUser.setUname(rawObj.getString("nickName"));
                addUser.setUgender(rawObj.getInteger("gender"));
                addUser.setUavatar(rawObj.getString("avatarUrl"));
                addUser.setUaddress(rawObj.getString("province") + rawObj.getString("city") + "," + rawObj.getString("country"));
                addUser.setUbalance(30);
                Integer userId = wxUserDao.addWxUser(addUser);
                respUser = addUser;
            }
        } catch (Exception e) {
            //todo handle this exception
            e.printStackTrace();
        }
        return result.success(respUser);
    }

    @Override
    public Result findBooks(Map<String, Object> data) {
        Result result = new Result();
        String is_all = data.get("is_all") == null ? "" :  data.get("is_all") + "";
        if ("1".equals(is_all)) {
            List<Book> books = wxUserDao.findAllBooks();
            return result.success(books);
        }
        return result.success();
    }

    @Override
    public Result findBookCommentById(Map<String, Object> data) {
        Result result = new Result();
        Map<String, Object> resultMap = new HashMap<>();
        String skey = data.get("skey") == null ? "" : data.get("skey") + "";
        String bookId = data.get("bookid") == null ? "" : data.get("bookid") + "";
        List<Comment> comments = wxUserDao.findCommentsByBookId(bookId);
        String is_buy = wxUserDao.findBookIsBuy(data);
        resultMap.put("is_buy",is_buy == null ? 0 : 1);
        resultMap.put("comments",comments);
        return result.success(resultMap);
    }

    @Override
    public Result addComment(Map<String, Object> data) {
        Result result = new Result();
        int num = wxUserDao.addComment(data);
        if (num > 0) {
            return result.success();
        } else {
            return result.failure();
        }
    }

    @Override
    public Result buyBook(Map<String, Object> data) {
        Result result = new Result();
        try {
            int num = wxUserDao.addOrderInfo(data);
            if (num > 0) {
                num = wxUserDao.updateUserInfo(data);
                if (num > 0) {
                    return result.success();
                }
            }
            return result.failure();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result getMyBooks(Map<String, Object> data) {
        Result result = new Result();
        List<Book> books = wxUserDao.getMyBooks(data);
        return result.success(books);
    }

}
