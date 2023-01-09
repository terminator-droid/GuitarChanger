package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.BrandDao;
import com.dudev.jdbc.starter.entity.Brand;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

public class BrandService {

    private static final BrandService INSTANCE = new BrandService();

    private final BrandDao brandDao = BrandDao.getInstance();

    private BrandService() {
    }

    public static BrandService getInstance() {
        return INSTANCE;
    }

    public Brand getBrand(int id) {
        return brandDao.findById(id).orElse(null);
    }

    public List<Brand> getBrandsByCategory(int category) {
        return brandDao.findByCategory(category);
    }

}
