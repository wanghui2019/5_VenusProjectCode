package com.hui.filter;

import com.hui.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest myRequest= (HttpServletRequest) req;
        User user= (User) myRequest.getSession().getAttribute("loginUser");

        if (user!=null){
            chain.doFilter(req, resp);
        }else {
            myRequest.setAttribute("msg","请登陆");
            myRequest.getRequestDispatcher("info.jsp").forward(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
