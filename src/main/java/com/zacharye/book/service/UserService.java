package com.zacharye.book.service;

import com.zacharye.book.entity.User;
import com.zacharye.book.util.Result;

import java.util.Map;

public interface UserService {
    User findUserByLoginName(String username);
    Result addUser(User user);
}
