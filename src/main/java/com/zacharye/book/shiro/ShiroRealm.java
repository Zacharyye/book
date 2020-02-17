package com.zacharye.book.shiro;

import com.zacharye.book.entity.master.User;
import com.zacharye.book.service.master.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 认证：登录的时候用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //查找用户
        User user = userService.findUserByLoginName(username);
        if(user == null){
            //用户不存在则抛出异常
            System.out.println("认证：当前登录的用户不存在");
            throw new UnknownAccountException();
        }

        String realName = getName();
        ByteSource salt;
        salt = ByteSource.Util.bytes(username);

        SimpleHash hash = new SimpleHash("MD5",user.getPassword(),salt,1024);
        return new SimpleAuthenticationInfo(user.getLogin_name(),user.getPassword(),salt,realName);
    }

    /**
     * 授权：用到权限的时候调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }
}
