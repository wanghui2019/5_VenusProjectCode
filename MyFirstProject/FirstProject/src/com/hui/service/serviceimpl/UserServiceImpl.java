package com.hui.service.serviceimpl;

import com.hui.dao.impl.UserDaoImpl;
import com.hui.domain.User;
import com.hui.service.UserService;
import com.hui.utils.MailUtils;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public void userRegister(User user) {
        UserDaoImpl userDao=new UserDaoImpl();
        //将注册的用户信息添加到数据库
        userDao.add(user);
        try {
            //发邮件
            MailUtils.sendMail(user.getEmail(),user.getCode());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public User userActive(String code) throws SQLException {
        UserDaoImpl userDao=new UserDaoImpl();
        //将通过激活码获得的用户信息返回给servlet
        return userDao.userActive(code);
    }

    //将修改后的信息进行修改
    public void updateUser(User user) throws SQLException {
        UserDaoImpl userDao=new UserDaoImpl();
        userDao.updateUser(user);
    }

    //登陆
    public User loginUser(User user) throws SQLException {
        UserDaoImpl userDao=new UserDaoImpl();
        return userDao.loginUser(user);
    }
}
