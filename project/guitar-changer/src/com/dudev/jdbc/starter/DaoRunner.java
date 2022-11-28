package com.dudev.jdbc.starter;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dto.ProductFilter;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.Product;

import java.util.List;
import java.util.UUID;

public class DaoRunner {

    public static void main(String[] args) {
        ProductDao daoInstance = ProductDao.getInstance();
        ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();
//        ProductFilter filter = new ProductFilter(2, 0, UUID.fromString("67805d38-d903-44c7-89e4-fd67047b8e33"), 0, false, 0, 0, null);
//
//        List<Product> all = daoInstance.findAll(filter);
//        System.out.println(all);

        System.out.println(daoInstance.findByPriceAndChangeType(30000, changeTypeDao.findById(2).orElse(null), 1));
    }
}
