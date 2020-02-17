package com.zacharye.book.service.master;

import com.zacharye.book.dao.master.UserDao;
import com.zacharye.book.entity.master.User;
import com.zacharye.book.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
