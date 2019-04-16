package com.hui.servlet;

import com.hui.domain.*;
import com.hui.service.OrderService;
import com.hui.service.serviceimpl.OrderServiceImpl;
import com.hui.utils.BaseServlet;
import com.hui.utils.UUIDUtil;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {
   public String addOrder(HttpServletRequest request,HttpServletResponse response) throws SQLException, ParseException {

       //先判断用户有没有登陆，如果没有登陆，提示用户先登陆
       User user = (User) request.getSession().getAttribute("loginUser");
       if (user==null){
           System.out.println("请先登陆你的账号");
       }


       //用户已经登陆了,创建order对象，存值
       Order order=new Order();
       order.setUser(user);
       //存订单编号，(商品图片路径，商品名称，商品单价，商品数量，商品小计)/商品列表项，商品总价/购物车
       order.setOid(UUIDUtil.getUUID());

       order.setOrdertime(new Date());
       Cart cart = (Cart) request.getSession().getAttribute("cart");
       order.setTotalPrice(cart.getProductsPrice());
       order.setState(1);
       //从购物车里拿出所有的商品列表项
       Collection<CartItem> cartItems = cart.getCartItems();
       //遍历
       for (CartItem cartItem:cartItems) {
           OrderItem orderItem=new OrderItem();
           //(存商品图片路径，商品名称，商品单价)/商品，商品数量，商品小计
           orderItem.setItemid(UUIDUtil.getUUID());
           orderItem.setProduct(cartItem.getProduct());
           orderItem.setTotal(cartItem.getProductprice());
           orderItem.setQuantity(cartItem.getNum());
           //把订单对象存进去，其实就是存订单编号
           orderItem.setOrder(order);

           //存完后将orderItem存到order对象里
           order.getOrderItems().add(orderItem);
       }

       //将数据存到数据库里
       OrderService orderService=new OrderServiceImpl();
       orderService.saveOrder(order);

       //所有的数据都以及存入到order对象中后，清空购物车
       cart.clearProduct();

       //把数据存到request里，然后跳转
       request.setAttribute("order",order);
       //跳转页面
       return "jsp/order_info.jsp";
   }


   public String MyOrder(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {
       //获取用户信息
       User user= (User) request.getSession().getAttribute("loginUser");
       //获取页数
       int curNum=Integer.parseInt(request.getParameter("num"));
       OrderServiceImpl orderService=new OrderServiceImpl();
       CalcuModel calcuModel=orderService.showOrder(user,curNum);

       request.setAttribute("cm",calcuModel);
       return "jsp/order_list.jsp";
   }
   //付钱状态
   public String payState(HttpServletRequest request,HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
       String oid = request.getParameter("oid");
       OrderService orderService=new OrderServiceImpl();
       Order order=orderService.payState(oid);

       request.setAttribute("order",order);

       return "jsp/order_info.jsp";
   }
}
