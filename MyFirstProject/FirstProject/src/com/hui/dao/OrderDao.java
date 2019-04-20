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


    //管理员查询所有订单信息
    List<Order> showAllOrder(int startNum,int showNum) throws SQLException;

    //管理员根据state查询订单信息
    List<Order> showAllOrder(int state,int startNum,int showNum) throws SQLException;

    //查询订单总数
    int showOrderTotal() throws SQLException;

    //查询相应状态的订单总数
    int showOrderTotal(int state) throws SQLException;

    //根据oid查询订单
    Order getOrder(String oid) throws SQLException;

    void updateState(Order order) throws SQLException;

}
