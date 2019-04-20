package com.hui.service;


import com.hui.domain.CalcuModel;
import com.hui.domain.Order;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface OrderService {
    //添加order和orderItem数据
    void saveOrder(Order order) throws SQLException;

    Order payState(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateMessage(String address,String name,String telephone,String oid) throws SQLException;

    void updateOrderState(String oid,int state) throws SQLException;

    CalcuModel showAllOrder(int curNum) throws SQLException;

    CalcuModel showAllOrder(int state,int curNum) throws SQLException;

    //根据oid查询订单
    Order getOrder(String oid) throws SQLException;

    //跟新数据库
    void updateState(Order order) throws SQLException;
}
