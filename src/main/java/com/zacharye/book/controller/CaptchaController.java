package com.zacharye.book.controller;

import com.zacharye.book.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 生成验证码
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController extends AbstractController {

    /**
     * 初始化验证码
     * @param request
     * @param response
     */
    @RequestMapping("/init")
    public void initCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 120,height = 30;
        int startInx = 25;
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,width - 1,height - 1);
        g.setColor(Color.GRAY);
        Random random = new Random();
        for(int i = 0; i < 40; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(10);
            int y2 = random.nextInt(10);
            g.drawLine(x1,y1,x1 + x2,y1 + y2);
        }
        //设置字体
        Font font = new Font("Time New Roman",Font.BOLD,20);
        g.setFont(font);
        //定义运算符
        String[] ops = {"+","-","*","="};
        //生成第一个操作数
        int num1 = random.nextInt(10);
        String strRand1 = String.valueOf(num1);
        int red1 = random.nextInt(255);
        int green1 = random.nextInt(255);
        int blue1 = random.nextInt(255);
        //第一个操作数
        g.setColor(new Color(red1,green1,blue1));
        g.drawString(strRand1, startInx * 0 + 8,18);
        //随机生成一个运算符数组中的下标，从而得到随机的一个运算符，这里是0~4之间的一个随机值。因为4是等号
        int op_num = random.nextInt(3);
        String strRand2 = (String) ops[op_num];
        int red2 = random.nextInt(255);
        int green2 = random.nextInt(255);
        int blue2 = random.nextInt(255);
        //画出操作符
        g.setColor(new Color(red2,green2,blue2));
        g.drawString(strRand2,startInx * 1 + 8 ,18);
        //生成0~8之间的一个数+1，作为第二个操作数，因为有可能出现除法，所以第二个操作数不能位0.所以+1，使数在1~9之间
        int num2 = (random.nextInt(9) + 1);
        String strRand3 = String.valueOf(num2);
        int red3 = random.nextInt(255);
        int green3 = random.nextInt(255);
        int blue3 = random.nextInt(255);
        //画出第二个操作数
        g.setColor(new Color(red3,green3,blue3));
        g.drawString(strRand3,startInx * 2 + 8 ,18);
        String strRand4 = (String) ops[3];
        int red4 = random.nextInt(255);
        int green4 = random.nextInt(255);
        int blue4 = random.nextInt(255);
        //画出等号
        g.setColor(new Color(red4,green4,blue4));
        g.drawString(strRand4, startInx * 3 + 8, 18);
        //由运算符的不同执行不同的运算，得到验证码结果值
        Integer randomCode = 0;
        switch (op_num){
            case 0:
                randomCode = num1 + num2;
                break;
            case 1:
                randomCode = num1 - num2;
                break;
            case 2:
                randomCode = num1 * num2;
                break;
            /*case 3:
                randomCode = num1 / num2;
                break;*/
        }
        //把运算符结果值存放在session中，用于验证码校对
        HttpSession session = request.getSession(true);
        session.setAttribute("randomCode",randomCode.toString());
        bufferedImage.flush();
        g.dispose();
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",out);
        out.close();
    }
}
