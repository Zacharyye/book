package com.zacharye.book.service;

import com.zacharye.book.dao.UserDao;
import com.zacharye.book.entity.User;
import com.zacharye.book.service.UserService;
import com.zacharye.book.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User findUserByLoginName(String username) {
        return userDao.findUserByLoginName(username);
    }

    @Override
    public Result addUser(User user) {
        Result<Object> result = new Result<>();
        int num = userDao.addUser(user);
        if (num == 0) {
            result.failure();
        } else {
            result.success();
        }
        return result;
    }
}
