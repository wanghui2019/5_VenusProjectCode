package com.hui.dao;

import com.hui.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> findAllCats() throws SQLException;

    void addCategory(Category category) throws SQLException;
}
