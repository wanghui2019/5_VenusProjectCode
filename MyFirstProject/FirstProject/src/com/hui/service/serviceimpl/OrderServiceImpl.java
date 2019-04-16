package com.hui.service.serviceimpl;

import com.hui.dao.OrderDao;
import com.hui.dao.impl.OrderDaoImpl;
import com.hui.domain.*;
import com.hui.service.OrderService;
import com.hui.utils.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    OrderDaoImpl orderDao=new OrderDaoImpl();
    //保证事务正常提交
    @Override
    public void saveOrder(Order order) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConn();
            //开启事务
            conn.setAutoCommit(false);
            //保存订单
            orderDao.addOrders(conn,order);
            //保存订单列表项目
            for (OrderItem orderItem:order.getOrderItems()) {
                orderDao.addOrderItem(conn,orderItem);
            }
            //提交事务
            conn.commit();
        } catch (SQLException e) {
            //回滚
            conn.rollback();
        }
    }
    //添加支付状态
    @Override
    public Order payState(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        Order order = orderDao.selectOrder(oid);

        //执行查询，得到order里的orderItem
        List<OrderItem> orderItems = orderDao.selectCartItem(oid);
        order.setOrderItems(orderItems);

        return order;
    }

    //该服务的作用是将order以及orderItem全部添加到CalcuModel里
    public CalcuModel showOrder(User user,int curNum) throws SQLException, InvocationTargetException, IllegalAccessException {
        //根据用户信息查询totalNum
        //select count(*) from orders where uid=?
        int totalNum=orderDao.getOrderTotal(user);
        CalcuModel calcuModel=new CalcuModel(curNum,totalNum,3);
        //执行以下方法，得到列表项,根据这是谁的订单，这个订单的这一页从啥开始，这一页显示多少个
        //执行后会得到一个集合，这个集合就是要放到calcuModel里的集合对象
        List<Order> orders=orderDao.findOrder(user,calcuModel.getStartNum(),calcuModel.getShowNum());
        //要放的集合
        calcuModel.setList(orders);
        //开始设置参数
        calcuModel.setUrl("OrderServlet?method=MyOrder");

        return calcuModel;
    }


    public void updateMessage(String address,String name,String telephone,String oid) throws SQLException {
        OrderDao orderDao=new OrderDaoImpl();
        orderDao.updateMessage(address,name,telephone,oid);
    }


    public void updateOrderState(String oid, int state) throws SQLException {
        OrderDao orderDao=new OrderDaoImpl();
        orderDao.updateOrderState(oid,state);
    }


}
