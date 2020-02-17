package com.zacharye.book.controller;

import com.zacharye.book.entity.master.Category;
import com.zacharye.book.entity.master.User;
import com.zacharye.book.service.master.ArticleService;
import com.zacharye.book.service.master.UserService;
import com.zacharye.book.util.FileUtils;
import com.zacharye.book.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
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

    @RequestMapping(value = "/upExcel",method = {RequestMethod.POST,RequestMethod.GET})
    public String showUpExcel(HttpServletRequest request,HttpServletResponse response){
        logger.info("upExcel");
        return "views/upExcel";
    }

    @RequestMapping(value = "/toUpExcel", method = {RequestMethod.POST})
    @ResponseBody
    public Result toUpExcel (HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile excel) throws IOException {
        //prepare the variable
        Result result = new Result();
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = new HashMap<>();
        String name = excel.getOriginalFilename();
        if (!name.endsWith(".xls") && !name.endsWith(".xlsx")) {
            logger.warn("File doesn't satisfy the condition.");
            result = result.failure();
        } else {
            //TODO analysis the excel
            FileUtils.getDataFromExcel(name,excel.getInputStream());
        }
        return result;
    }

    @RequestMapping(value = "/toFindProduct", method = {RequestMethod.GET})
    @ResponseBody
    public Result toFindProductInfo (HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String str = "1、代发理财  专属优享  （1万起购）|名称;产品代码;;产品期限;销售日期;;业绩基准;|新享理财·稳健周期91天A;B160A0417;;91.0;每周一10:00;;0.0435;|代发专属·乐赢稳健周期91天;B170A0033;;3个月;每周二;;0.041;|代发专属·乐赢稳健和信;A193A3969;;3个月;11.15-11.21;;0.041;|代发专属·乐赢稳健周期;A160A0419;;6个月;11.18开放;;0.0415;|;;;;;;;|@2、热销明星  超值收益  （1万起购）|名称;产品代码;;产品期限;销售日期;;业绩基准;|本周爆款·双赢稳健多资产定开1号;A193A5396;;2年;11.8-11.21;;0.047;|稳健和信1962;A193A3970;;3个月;11.15-11.21;;0.04;|;;;;;;;|@3、天天理财  灵活便捷 （1万起购）|名称;销售日期;;;;本期收益/累计年化;;|随用随取·薪金煲-天天利;工作日8:30-15:00;;;;3.10%-3.60%;;|;7*24小时实时赎回;;;;;;|乐赢稳健中短债;每日5亿;;;;近90天年化4.60%;;|;;;;;;;|@4、结构性存款  保本安心 （1万起购）|名称;产品期限;;销售日期;;预期/基准年化;;|乐赢汇率挂钩人民币结构性存款1999期;98.0;;11.18-11.21;;3.75%-3.95%;;|;;;;;;;|@5、大额存单  保本保息|名称;产品期限;;起购金额;;预期/基准年化;;|定制定期存款;3年;;1万;;0.039;;|一次付息大额存单;3年;;20万/50万;;3.95%/4.0%;;|月月息;3年;;50万;;0.0393;;|;;;;;;;|@6、贵金属  投资收藏|名称;产品亮点;;;;价格;;|年度最热金币·2020年熊猫金币;人民银行发行，设计师签名，可回购;;;;以上线价格为准;;|投资金条·中信金;市场不确定因素增加，避险保值需求增加，黄金投资正当时;;;;优惠：基础金价+9元/克;;|积存金;存黄金有利息，1年定期1.38%\n" +
                "（克重计息）、0.01克起存;;;;详见每日基础金价;;|;;;;;;;|";
        result.setData(str);
        return result;
    }

    @RequestMapping(value = "/toShowProduct", method = {RequestMethod.GET})
    public String toShowProductInfo (HttpServletRequest request, HttpServletResponse response) {
       return "views/productInfo";
    }
}
