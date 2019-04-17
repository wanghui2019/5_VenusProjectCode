package com.hui.dao;

import com.hui.domain.CalcuModel;
import com.hui.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    //热门商品
    List<Product> findHost() throws SQLException;
    //最新商品
    List<Product> findNew() throws SQLException;
    //查询单个商品详情
    Product findProductOne(String pid) throws SQLException;
    //分页查新商品
    List<Product> findProduct(String cid, int startNum,int showNum) throws SQLException;

    //查询某个商品总数
    int findTotal(String cid) throws SQLException;

    //查询所有商品信息
    int showTotal() throws SQLException;

    //根据分页查询集合
    List<Product> showProduct(int startNum,int showNum) throws SQLException;

    void saveProduct(Product product) throws SQLException;
}
