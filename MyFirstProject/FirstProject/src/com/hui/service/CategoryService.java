package com.hui.service;

import com.hui.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> findAllCats() throws SQLException;
}
