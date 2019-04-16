package com.hui.dao.impl;

import com.hui.dao.CategoryDao;
import com.hui.domain.Category;
import com.hui.utils.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAllCats() throws SQLException {
        String sql="select * from category";
        QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSources());
        return queryRunner.query(sql,new BeanListHandler<Category>(Category.class));
    }
}
