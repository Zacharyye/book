package com.zacharye.book.controller;

import com.zacharye.book.service.cluster.WxUserService;
import com.zacharye.book.util.RequestUtil;
import com.zacharye.book.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wxuser")
@Controller
public class WxUserController extends AbstractController {

    @Autowired
    private WxUserService wxUserService;

    /**
     * 微信小程序用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Result login (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getAllRequestParameters(request);
        return wxUserService.findUserByOpenId(dataMap);
    }

    /**
     * 获取书单
     */
    @RequestMapping(value = "/book/getBooks", method = RequestMethod.GET)
    @ResponseBody
    public Result getBookList (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getAllRequestParameters(request);
        return wxUserService.findBooks(dataMap);
    }
    /**
     * 查询指定书籍
     * @author: zachary
     * @Created: 2019-12-17 10:55
     */
    @RequestMapping(value = "/book/queryBook", method = RequestMethod.GET)
    @ResponseBody
    public Result queryBookById (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getAllRequestParameters(request);
        return wxUserService.findBookCommentById(dataMap);
    }

    /**
     * 写评论
     * @author: zachary
     * @Created: 2019-12-17 11:16
     */
    @RequestMapping(value = "/comment/write", method = RequestMethod.POST)
    @ResponseBody
    public Result addComment (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getRequestBodyParams(request);
        return wxUserService.addComment(dataMap);
    }

    /**
     * 生成订单
     * @author: zachary
     * @Created: 2019-12-17 13:53
     */
    @RequestMapping(value = "/order/buy" , method = RequestMethod.POST)
    @ResponseBody
    public Result buyBook (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getRequestBodyParams(request);
        return wxUserService.buyBook(dataMap);
    }

    /**
     * 获取指定用户已购书籍列表
     * @author: zachary
     * @Created: 2019-12-17 13:53
     */
    @RequestMapping(value = "/getBoughtBooks", method = RequestMethod.GET)
    @ResponseBody
    public Result getMyBooks (HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = RequestUtil.getAllRequestParameters(request);
        return wxUserService.getMyBooks(dataMap);
    }

}
