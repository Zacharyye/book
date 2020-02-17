package com.zacharye.book.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 对请求所做的一些操作
 */
public class RequestUtil {
    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);

    public static Map<String,Object> getAllRequestParameters(HttpServletRequest request){
        Enumeration enumeration = request.getParameterNames();
        Map<String,Object> params = new HashMap<String, Object>();
        String key = "";
        String value = "";
        while(enumeration.hasMoreElements()){
            key = enumeration.nextElement() + "";
            value = request.getParameter(key);
            params.put(key,value);
        }
        return params;
    }

    public static JSONObject getRequestBodyParams (HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            StringBuilder str = new StringBuilder();
            while (inputStream.read(buffer) != -1) {
                str.append(new String(buffer,"utf-8"));
            }
            return JSONObject.parseObject(str.toString());
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("GET REQUESTBODY PARAMS ERROR:" + e.getMessage(),e);
        }
        return null;

    }

}
