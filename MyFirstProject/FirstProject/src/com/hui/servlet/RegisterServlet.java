package com.hui.servlet;


import com.hui.domain.User;
import com.hui.service.serviceimpl.UserServiceImpl;
import com.hui.utils.BaseServlet;
import com.hui.utils.MyBeanUtil;
import com.hui.utils.MyBeanUtils;
import com.hui.utils.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@javax.servlet.annotation.WebServlet(name = "RegisterServlet")
public class RegisterServlet extends BaseServlet {
    public String add(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        MyBeanUtils.populate(user,map);
        //其他数据手动设置
        user.setState(0);
        user.setUid(UUIDUtil.getUUID());
        user.setCode(UUIDUtil.getUUID64());

        UserServiceImpl dao=new UserServiceImpl();
        dao.userRegister(user);

        request.setAttribute("msg","用户注册成功，请激活");

        return "jsp/login.jsp";
    }

    public String active(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");

        UserServiceImpl codeDao=new UserServiceImpl();
        User user = null;
        try {
            user = codeDao.userActive(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //判断user是否为空，如果不为空，存在该用户，将激活码设置为空，将激活状态设置为1
        if (user!=null){
            user.setCode("");
            user.setState(1);
            try {
                codeDao.updateUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "jsp/index.jsp";
    }

    //登陆
    public String loginUser(HttpServletRequest request, HttpServletResponse response){
        //获取提交来的数据，并将其放入到user中
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        MyBeanUtil.populate(user,map);

        UserServiceImpl userdao=new UserServiceImpl();
        //user1为传回来的数据
        try {
            User user1=userdao.loginUser(user);
            if (user1!=null && user1.getState()==1){
                HttpSession session = request.getSession();
                session.setAttribute("loginUser",user1);

                response.sendRedirect("index.jsp");
                return null;
            }else if (user1==null){
                request.setAttribute("msg","用户名或者密码不正确");
                return "jsp/login.jsp";
            }else if (user1!=null && user1.getState()==0){
                request.setAttribute("msg","用户未激活");
                return "jsp/login.jsp";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //退出
    public String layout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        HttpSession session = request.getSession();
        session.invalidate();
        //重新定向到首页
        response.sendRedirect("index.jsp");
        return null;
    }
}
