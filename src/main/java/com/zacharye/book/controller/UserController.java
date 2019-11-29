package com.zacharye.book.controller;

import com.zacharye.book.controller.AbstractController;
import com.zacharye.book.entity.Category;
import com.zacharye.book.entity.User;
import com.zacharye.book.service.ArticleService;
import com.zacharye.book.service.UserService;
import com.zacharye.book.util.RequestUtil;
import com.zacharye.book.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;


    @RequestMapping(value="/login",method = {RequestMethod.GET,RequestMethod.POST})
    public String login(HttpServletRequest request, HttpServletResponse response, Map<String,Object> data) throws ServletException, IOException {
        //登录界面
        data.put("login_error",0);
        data.put("username",0);
        return "views/lo_gin";
    }

    @RequestMapping(value="/register",method = {RequestMethod.GET,RequestMethod.POST})
    public String register(HttpServletRequest request, HttpServletResponse response, Map<String,Object> data) throws ServletException, IOException {
        //登录界面
        return "views/to_reg";
    }

    @RequestMapping(value="/toIndex",method = {RequestMethod.GET,RequestMethod.POST})
    public String toIndex(HttpServletRequest request,HttpServletResponse response, Map<String, Object> data){
        //登录方法
//        System.out.println("toIndex");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifycode = request.getParameter("verifycode");
        HttpSession session = request.getSession(true);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            if(!subject.isAuthenticated()){
                subject.login(token);
            }
            data.put("username",subject.getPrincipal());
//            Object randomCode = session.getAttribute("randomCode");
//            if(randomCode == null){
//                throw new RuntimeException();
//            } else if(!verifycode.equals(randomCode)) {
//                request.setAttribute("username",username);
//                request.setAttribute("password",password);
//                request.setAttribute("login_error","3");
//                data.put("login_error",3);
//                return "login";
//            }
            session.setAttribute("username",username);
        } catch (UnknownAccountException e) {
            //无此账号
            data.put("login_error",1);
            data.put("username",username);
            return "views/lo_gin";
        } catch (IncorrectCredentialsException e) {
            //密码错误
            data.put("login_error",2);
            data.put("username",username);
            return "views/lo_gin";
        } catch (Exception e){
            data.put("login_error",2);
            data.put("username",username);
            return "views/lo_gin";
        }
        //查询相关数据
        List<Category> categoryList = articleService.findCategories(username);
        request.setAttribute("categoryList",categoryList);
        return "views/userIndex";
    }

    @RequestMapping(value = "/index",method = {RequestMethod.POST,RequestMethod.GET})
    public String showIndex(HttpServletRequest request,HttpServletResponse response){
        logger.info("index");
        return "views/index_a";
    }

    @RequestMapping(value = "/userIndex",method = {RequestMethod.POST,RequestMethod.GET})
    public String showUserIndex(HttpServletRequest request,HttpServletResponse response){
        return "views/userIndex";
    }

    @RequestMapping(value = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HttpSession session = request.getSession(false);
        request.removeAttribute("username");
        if(session != null)
            session.removeAttribute("username");
        return "views/index_a";
    }

    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    public Result userAdd(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data){
        String username = request.getParameter("username");
        User user = new User();
        user.setLogin_name(username);
        user.setNickname(request.getParameter("nickname"));
        //加密
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = request.getParameter("password");//密码原值
        Object salt = ByteSource.Util.bytes(username);//盐值
        int hashIterations = 1024;//加密1024次
        String pwd = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations).toString();
        user.setPassword(pwd);
        Result userResult = userService.addUser(user);
        return userResult;
    }

    @RequestMapping(value = "/findUser",method = {RequestMethod.GET})
    @ResponseBody
    public Result findUser(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data){
        String username = request.getParameter("username");
        User user = userService.findUserByLoginName(username);
        return new Result().success(user);
    }

}
