package com.hui.dao;

import com.hui.domain.User;

import java.sql.SQLException;

public interface UserDao {
    //用户注册
    void add(User user);

    //用户账号激活
    User userActive(String code) throws SQLException;

    //更新数据
    void updateUser(User user) throws SQLException;

    //登陆
    User loginUser(User user) throws SQLException;
}
