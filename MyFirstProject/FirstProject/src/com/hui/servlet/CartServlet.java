package com.hui.servlet;

import com.hui.domain.Cart;
import com.hui.domain.CartItem;
import com.hui.domain.Product;
import com.hui.service.serviceimpl.ProductServiceImpl;
import com.hui.utils.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "CartServlet")
public class CartServlet extends BaseServlet {
    public String addProduct(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        //第一次访问时，没有Cart对象，就先创建
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart==null){
            //如果没有就创建
            cart=new Cart();
            //把cart放到session里，取名为cart
            request.getSession().setAttribute("cart",cart);

        }

        String pid = request.getParameter("pid");
        int num = Integer.parseInt(request.getParameter("quantity"));
        ProductServiceImpl productService=new ProductServiceImpl();
        //获取查询到个商品信息,并添加到商品列表里
        CartItem cartItem=new CartItem();
        cartItem.setProduct(productService.findProductOne(pid));
        cartItem.setNum(num);

        //接下来将cartItem存放到cart里
        cart.addProduct(cartItem);

        response.sendRedirect("jsp/cart.jsp");

        return null;
    }
    
    //删除某个商品项目
    public String removeProduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pid = request.getParameter("id");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeProduct(pid);

        response.sendRedirect("jsp/cart.jsp");
        return null;
    }

    //清空购物车
    public String clearProduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.clearProduct();

        response.sendRedirect("jsp/cart.jsp");
        return null;
    }


    //改变某种商品购物数量
    public String changeProductNum(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        int num = Integer.parseInt(request.getParameter("num"));
        //获取购物车
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        cart.changeProductNum(pid,num);

        response.sendRedirect("jsp/cart.jsp");
        return null;
    }
}
