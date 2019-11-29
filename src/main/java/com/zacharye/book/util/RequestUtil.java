package com.zacharye.book.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 对请求所做的一些操作
 */
public class RequestUtil {

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

}
