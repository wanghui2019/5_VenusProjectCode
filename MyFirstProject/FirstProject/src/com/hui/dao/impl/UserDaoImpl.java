package com.hui.dao.impl;

import com.hui.dao.UserDao;
import com.hui.domain.User;
import com.hui.utils.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public void add(User user) {
        //sql语句
        String sql="insert into user(uid,username,password,name,email,birthday,sex,code) values(?,?,?,?,?,?,?,?)";
        //Object
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());

        try {
            queryRunner.update(sql,
                    user.getUid(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getCode()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //用户激活
    public User userActive(String code) throws SQLException {
        //查询数据库里该激活码有没有用户信息
        String sql="select * from user where code=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),code);
    }

    //更新数据
    public void updateUser(User user) throws SQLException {
        String sql="update user set state=?,code=? where uid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        queryRunner.update(sql,user.getState(),user.getCode(),user.getUid());
    }

    //登陆
    public User loginUser(User user) throws SQLException {
        String sql="select * from user where username=? and password=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
    }
}
