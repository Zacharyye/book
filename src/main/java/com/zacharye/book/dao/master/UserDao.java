package com.zacharye.book.dao.master;


import com.zacharye.book.entity.master.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User findUserByLoginName(String username);
    int addUser(User user);
}
