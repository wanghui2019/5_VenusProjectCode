package com.hui.service;

import com.hui.domain.User;

import java.sql.SQLException;

public interface UserService {
    //注册
    void userRegister(User user);

    //激活
    User userActive(String code) throws SQLException;

    //更新数据
    void updateUser(User user) throws SQLException;

    //登陆
    User loginUser(User user) throws SQLException;
}
