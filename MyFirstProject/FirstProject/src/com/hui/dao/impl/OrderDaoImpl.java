package com.hui.dao.impl;

import com.hui.dao.OrderDao;
import com.hui.domain.*;
import com.hui.utils.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class OrderDaoImpl implements OrderDao {
    //1.将order里的值存到数据库里
    @Override
    public void addOrders(Connection conn,Order order) throws SQLException {
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        queryRunner.update(sql,
                order.getOid(),
                order.getOrdertime(),
                order.getTotalPrice(),
                order.getState(),
                order.getAddress(),
                order.getName(),
                order.getTelephone(),
                order.getUser().getUid()
                );
    }
    //2。将orderItem里的值存到数据库里
    @Override
    public void addOrderItem(Connection conn,OrderItem orderItem) throws SQLException {
        String sql="insert into orderitem values(?,?,?,?,?)";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        queryRunner.update(sql,
                orderItem.getItemid(),
                orderItem.getQuantity(),
                orderItem.getTotal(),
                orderItem.getProduct().getPid(),
                orderItem.getOrder().getOid()
                );
    }

    public int getOrderTotal(User user) throws SQLException {
        String sql="select count(*) from orders where uid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        Long curNum= (Long) queryRunner.query(sql,new ScalarHandler(),user.getUid());
        return curNum.intValue();
    }

    //该方法用于将Order集合对象返回
    public List<Order> findOrder(User user, int startNum, int showNum) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql="select * from orders where uid=? limit ?,?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startNum, showNum);

        //接下来查询各个订单下的订单项，并将其分别添加到orderList集合里
        for (Order order:orderList) {                                   //order指的是订单
            //得到每一个orderList集合下的order，根据order查询下面的orderItem以及orderItem对应的商品
            String sqlOrder="select * from orderitem o,product p where o.pid=p.pid and oid=?";
            QueryRunner queryRunner1=new QueryRunner(JDBCUtil.getDataSources());
            List<Map<String, Object>> orderProducts = queryRunner1.query(sqlOrder, new MapListHandler(), order.getOid());
            //对orderProducts进行遍历，得到每个订单下的每个订单详情，orderProducts指的是订单下的商品信息
            for (Map<String, Object> map:orderProducts) {              //map指的是订单下的一种商品信息,该商品以map格式存储

                    //创建orderItem和product对象
                    OrderItem orderItem=new OrderItem();
                    Product product=new Product();
                    //时间类型格式的转换
                    //1.创建时间类型的转换器
                    DateConverter dt=new DateConverter();
                    //2。设置转换格式
                    dt.setPattern("yyyy-MM-dd");
                    //3.注册转换器
                    ConvertUtils.register(dt,java.util.Date.class);

                    BeanUtils.populate(orderItem, map);
                    BeanUtils.populate(product, map);

                    //将product存到orderItem上
                    orderItem.setProduct(product);
                    //将orderItem存到order的List<orderItem>下
                    order.getOrderItems().add(orderItem);
            }
        }
        //将orderList集合返回
        return orderList;
    }

    @Override
    public List<OrderItem> selectCartItem(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql="select * from orderitem o,product p where o.pid=p.pid and oid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        List<Map<String, Object>> cartLists = queryRunner.query(sql, new MapListHandler(),oid);
        List<OrderItem> list=new ArrayList<>();
        for (Map<String, Object> maps:cartLists) {
            OrderItem orderItem=new OrderItem();
            Product product=new Product();

            BeanUtils.populate(product,maps);
            BeanUtils.populate(orderItem,maps);
            orderItem.setProduct(product);

            list.add(orderItem);
        }
        return list;
    }

    public Order selectOrder(String oid) throws SQLException {
        String sql="select * from orders where oid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);

        String sql1="select total from orders where oid=?";
        QueryRunner queryRunner1=new QueryRunner(JDBCUtil.getDataSources());
        double totalPrice = (double) queryRunner1.query(sql1, new ScalarHandler(), oid);

        order.setTotalPrice(totalPrice);

        return order;
    }


    public void updateMessage(String address,String name,String telephone,String oid) throws SQLException {
        String sql="update orders set address=?,name=?,telephone=? where oid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        queryRunner.update(sql,address,name,telephone,oid);
    }

    public void updateOrderState(String oid,int state) throws SQLException {
        String sql="update orders set state=? where oid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        queryRunner.update(sql,state,oid);
    }

}
