package com.hui.dao;

import com.hui.domain.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    //添加order数据
    void addOrders(Connection conn,Order order) throws SQLException;

    //添加orderItem里的数据
    void addOrderItem(Connection conn,OrderItem orderItem) throws SQLException;

    //查询order订单集合订单下的订单项
    List<Order> findOrder(User user, int startNum, int showNum) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<OrderItem> selectCartItem(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;
    //更新地址，姓名，电话
    void updateMessage(String address,String name,String telephone,String oid) throws SQLException;
    //跟新订单状态
    void updateOrderState(String oid,int state) throws SQLException;
}
