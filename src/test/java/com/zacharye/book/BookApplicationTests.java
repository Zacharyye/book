package com.zacharye.book;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void shiroTest () {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "123456";//密码原值
        Object salt = ByteSource.Util.bytes("Zachary");//盐值
        int hashIterations = 1024;//加密1024次
        String result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations).toString();
        System.out.println(result);
    }

}
