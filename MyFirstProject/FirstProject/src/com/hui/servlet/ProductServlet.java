package com.hui.servlet;

import com.hui.domain.CalcuModel;
import com.hui.domain.Product;
import com.hui.service.serviceimpl.ProductServiceImpl;
import com.hui.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet")
public class ProductServlet extends BaseServlet {
    //调用service层获取返回的商品集合
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ProductServiceImpl productService=new ProductServiceImpl();
        //查询热门商品，并将查询结果返回
        List<Product> hostProduct = productService.findHost();
        request.setAttribute("hostProduct",hostProduct);
        //查询最新商品，并将查询结果返回
        List<Product> newProduct = productService.findNew();
        request.setAttribute("newProduct",newProduct);

        return "jsp/index.jsp";
    }
    //点击商品获取商品详情
    public String findProductOne(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String pid = request.getParameter("pid");

        ProductServiceImpl productService=new ProductServiceImpl();
        Product productOne = productService.findProductOne(pid);

        request.setAttribute("product",productOne);

        return "jsp/product_info.jsp";
    }

    public String findProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //获取分类cid
        String cid = request.getParameter("cid");
        //获取页数
        int curNum = Integer.parseInt(request.getParameter("num"));
        //执行查询
        ProductServiceImpl productService=new ProductServiceImpl();
        CalcuModel calcuModel = productService.findProduct(cid,curNum);
        //存到request里
        request.setAttribute("cm",calcuModel);

        return "jsp/product_list.jsp";
    }




}
