package com.hui.service.serviceimpl;

import com.hui.dao.CategoryDao;
import com.hui.dao.impl.CategoryDaoImpl;
import com.hui.domain.Category;
import com.hui.service.CategoryService;
import com.hui.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAllCats() throws SQLException {
        CategoryDaoImpl category=new CategoryDaoImpl();
        return category.findAllCats();
    }

    public void addCategory(Category category) throws SQLException {
        CategoryDao categoryDao=new CategoryDaoImpl();
        categoryDao.addCategory(category);
        //把以前的redis数据清除
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }
}
