package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.CategoryDao;
import com.dudev.jdbc.starter.entity.Category;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

public class CategoryService {

    private static final CategoryService INSTANCE = new CategoryService();

    private final CategoryDao categoryDao = CategoryDao.getInstance();

    private CategoryService() {
    }

    @SneakyThrows
    public Category getCategoryById(int id) {
        return categoryDao.findById(id).orElseThrow();
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public static CategoryService getInstance() {
        return INSTANCE;
    }
}
