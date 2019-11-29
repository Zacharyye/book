package com.zacharye.book.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author: zachary
 * @Created: 2019-11-08 15:19
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter (SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/user/login");
        bean.setUnauthorizedUrl("/user/login");

        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/user/login","anon");
        filterChainMap.put("/user/register","anon");
        filterChainMap.put("/user/add","anon");
        filterChainMap.put("/user/findUser","anon");
        filterChainMap.put("/user/toIndex","anon");
        filterChainMap.put("/user/index","anon");
        filterChainMap.put("/captcha/init","anon");
        filterChainMap.put("/static/**","anon");
        filterChainMap.put("/css/**","anon");
        filterChainMap.put("/js/**","anon");
        filterChainMap.put("/images/**","anon");
        filterChainMap.put("/plugins/**","anon");
        filterChainMap.put("/logout","anon");
        filterChainMap.put("/article/**","anon");
        filterChainMap.put("/**","authc");

        bean.setFilterChainDefinitionMap(filterChainMap);
        return bean;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm(matcher));
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm (@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        return matcher;
    }
}
