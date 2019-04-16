package com.hui.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通用的工具类，使用反射得到

        //1.获取外面传来的一个方法
        String method = request.getParameter("method");
        if (method==null || "".equals(method) || method.trim().equals("")){
            method="execute";
        }
        //2。判断我的方法里有没有这个方法
        Class clazz = this.getClass();
        String path=null;
        try {
            Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            //3。有的话就执行该方法，并返回一个路径用于跳转
            if (md!=null){
                path = (String) md.invoke(this, request, response);
            }
            if (path!=null){
                request.getRequestDispatcher(path).forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String execute(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        return null;
    }
}
