package com.zacharye.book.service.master;

import com.zacharye.book.entity.master.User;
import com.zacharye.book.util.Result;

public interface UserService {
    User findUserByLoginName(String username);
    Result addUser(User user);
}
