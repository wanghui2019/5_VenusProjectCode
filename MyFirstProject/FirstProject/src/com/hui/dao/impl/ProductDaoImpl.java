package com.hui.dao.impl;

import com.hui.dao.ProductDao;
import com.hui.domain.CalcuModel;
import com.hui.domain.Product;
import com.hui.utils.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    //查询热门商品
    @Override
    public List<Product> findHost() throws SQLException {
        String sql="select * from product where is_hot=0 limit 0,9";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class));
    }
    //查询最新商品
    @Override
    public List<Product> findNew() throws SQLException {
        String sql="select * from product order by pdate desc limit 0,9";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class));
    }
    //查询单个商品详情
    @Override
    public Product findProductOne(String pid) throws SQLException {
        String sql="select * from product where pid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanHandler<Product>(Product.class),pid);
    }

    //查询商品详情
    @Override
    public List<Product> findProduct(String cid, int startNum,int showNum) throws SQLException {
        String sql="select * from product where cid=? limit ?,?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),cid,startNum,showNum);
    }
    //查询某个商品一共有多少个
    public int findTotal(String cid) throws SQLException {
        String sql="select count(*) from product where cid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        Long totalNum=(Long) queryRunner.query(sql,new ScalarHandler(),cid);
        return totalNum.intValue();
    }





}
