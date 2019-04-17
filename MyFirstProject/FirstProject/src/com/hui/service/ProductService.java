package com.hui.service;

import com.hui.domain.CalcuModel;
import com.hui.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    //热门商品
    List<Product> findHost() throws SQLException;
    //最新商品
    List<Product> findNew() throws SQLException;
    //查询单个商品详情
    Product findProductOne(String pid) throws SQLException;
    //分页查询
    CalcuModel findProduct(String pid, int curNum) throws SQLException;

    //根据分页查询集合
    CalcuModel showProduct(int curNum) throws SQLException;

    //保存商品信息到数据库
    void saveProduct(Product product) throws SQLException;
}
