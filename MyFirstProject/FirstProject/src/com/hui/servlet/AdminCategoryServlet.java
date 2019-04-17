package com.hui.servlet;

import com.hui.domain.Category;
import com.hui.service.CategoryService;
import com.hui.service.serviceimpl.CategoryServiceImpl;
import com.hui.utils.BaseServlet;
import com.hui.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    //查询分类信息
    public String showCategory(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        CategoryService categoryService=new CategoryServiceImpl();
        List<Category> allCats = categoryService.findAllCats();

        request.setAttribute("allCats",allCats);
        return "admin/category/list.jsp";
    }

    //添加分类信息
    public String addCategory(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        Category category=new Category();
        String cid = UUIDUtil.getUUID();
        category.setCid(cid);
        String cname=request.getParameter("cname");
        category.setCname(cname);

        CategoryService categoryService=new CategoryServiceImpl();
        categoryService.addCategory(category);

        response.sendRedirect("AdminCategoryServlet?method=showCategory");
        return null;
    }
}
