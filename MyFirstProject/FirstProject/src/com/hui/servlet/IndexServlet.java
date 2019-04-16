package com.hui.servlet;

import com.hui.domain.Category;
import com.hui.service.serviceimpl.CategoryServiceImpl;
import com.hui.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IndexServlet")
public class IndexServlet extends BaseServlet {
    //目的，在页面跳转到真实主页前，拿到分类信息的数据，放到request里面
    public String execute(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        CategoryServiceImpl categoryService=new CategoryServiceImpl();
        List<Category> allCats = categoryService.findAllCats();
        //将集合放入到request里

        request.setAttribute("allCats",allCats);

        return "jsp/index.jsp";
    }
}
