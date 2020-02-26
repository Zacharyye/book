package com.zacharye.book.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * http请求
 * @author: zachary
 * @Created: 2019-12-16 13:40
 */
public class RestHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(RestHttpUtils.class);

    public static JSONObject httpGet (String url) {
        logger.info("HTTPGET : " +url);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        logger.info("HTTPGET RESPONSE :" + response);
        return JSONObject.parseObject(response);
    }
}
