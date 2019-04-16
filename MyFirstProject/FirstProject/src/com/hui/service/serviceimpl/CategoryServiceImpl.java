package com.hui.service.serviceimpl;

import com.hui.dao.impl.CategoryDaoImpl;
import com.hui.domain.Category;
import com.hui.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAllCats() throws SQLException {
        CategoryDaoImpl category=new CategoryDaoImpl();
        return category.findAllCats();
    }
}
