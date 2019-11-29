package com.zacharye.book.dao;


import com.zacharye.book.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User findUserByLoginName(String username);
    int addUser(User user);
}
