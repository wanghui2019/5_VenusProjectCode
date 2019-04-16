package com.hui.servlet;

import com.hui.domain.Category;
import com.hui.service.serviceimpl.CategoryServiceImpl;
import com.hui.utils.BaseServlet;
import com.hui.utils.JedisUtils;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet")
public class CategoryServlet extends BaseServlet {
    //获取从ajax传来的请求，有这个请求的方法，就执行以下方法
    public String findCats(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        //使用redis作缓存
        //1.获取redis对象
        Jedis jedis = JedisUtils.getJedis();
        //2。根据键获得值，如果值为空，则内存中没有该键值，先从数据库拿，拿到后放到内存中
        String jedisArr = jedis.get("allCats");
        if (jedisArr==null || "".equals(jedisArr)){
            //1。先从服务层拿到数据
            CategoryServiceImpl categoryService=new CategoryServiceImpl();
            List<Category> allCats = categoryService.findAllCats();
            //将拿到的数据转换为json格式，然后响应给ajax，json需要导包
            //1,将集合全部转换为json格式
            String jsonArr = JSONArray.fromObject(allCats).toString();
            //放到内存里区
            jedis.set("allCats",jsonArr);
            //2。将信息响应到客户端，响应前告诉浏览器这是json格式的字符串
            response.setContentType("application/json;charset=utf-8");
            //print可以打印对象，writer不能打印对象，所以这里使用print
            response.getWriter().print(jsonArr);
        }else {
            //如果有，直接从内存里获取
            //2。将信息响应到客户端，响应前告诉浏览器这是json格式的字符串
            response.setContentType("application/json;charset=utf-8");
            //print可以打印对象，writer不能打印对象，所以这里使用print
            //直接把内存里的数据响应出去
            response.getWriter().print(jedisArr);
        }




        return null;
    }
}
