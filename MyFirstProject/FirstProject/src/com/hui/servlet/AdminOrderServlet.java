package com.hui.servlet;

import com.hui.domain.CalcuModel;
import com.hui.domain.Order;
import com.hui.domain.OrderItem;
import com.hui.service.OrderService;
import com.hui.service.serviceimpl.OrderServiceImpl;
import com.hui.utils.BaseServlet;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    public String showOrder(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        /*
        获取状态state
        判断state是否为空，如果为空，就执行调用service的方法查询所有订单信息
        如果不为空，就根据state的值调用service的方法查询相应状态的信息
         */
        OrderService orderService=new OrderServiceImpl();
        int state = Integer.parseInt(request.getParameter("state"));
        int curNum = Integer.parseInt(request.getParameter("num"));
        if (state==0){
            CalcuModel calcuModel=orderService.showAllOrder(curNum);
            //将其放到request里返回
            request.setAttribute("cm",calcuModel);
            return "admin/order/list.jsp";
        }else {
            CalcuModel calcuModel=orderService.showAllOrder(state,curNum);
            //将其放到request里返回
            request.setAttribute("cm",calcuModel);
            return "admin/order/list.jsp";
        }
    }


    //修改订单状态
    public String changeOrderState(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        //先获取订单编号
        String oid = request.getParameter("oid");
        int curNum=Integer.parseInt(request.getParameter("num"));
        //根据订单id修改订单状态。1，获取该订单  2，修改状态
        OrderService orderService=new OrderServiceImpl();
        Order order=orderService.getOrder(oid);
        int state = order.getState();
        order.setState(3);
        //将修改后的状态加到数据库里
        orderService.updateState(order);

        response.sendRedirect("AdminOrderServlet?method=showOrder&state="+state+"&num="+curNum);
        return null;
    }

    //查询商品的详细信息
    public String findProductDetail(HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String oid = request.getParameter("oid");
        //根据oid查询该订单的订单列表项
        OrderService orderService=new OrderServiceImpl();
        List<OrderItem> orderItems = orderService.payState(oid).getOrderItems();
        //将该订单列表项转换为json格式
        String items = JSONArray.fromObject(orderItems).toString();
        //2。将信息响应到客户端，响应前告诉浏览器这是json格式的字符串
        response.setContentType("application/json;charset=utf-8");
        //print可以打印对象，writer不能打印对象，所以这里使用print
        response.getWriter().print(items);
        return null;
    }
}
