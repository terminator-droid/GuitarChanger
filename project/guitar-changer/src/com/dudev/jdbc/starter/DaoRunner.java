package com.dudev.jdbc.starter;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.services.ProductService;

import java.util.List;
import java.util.UUID;

public class DaoRunner {

    public static void main(String[] args) {
        ProductDao daoInstance = ProductDao.getInstance();
        ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();

//        ProductService instance = ProductService.getInstance();
//        List<ProductDto> allProducts = instance.getAllProducts();
//        System.out.println(allProducts);
        ProductService instance1 = ProductService.getInstance();

        Dto byId = instance1.findById(UUID.fromString("79032a47-8893-4353-a45b-e3fbdff163fb"));
        System.out.println(byId);
//        ProductFilter filter = new ProductFilter(2, 0, UUID.fromString("67805d38-d903-44c7-89e4-fd67047b8e33"), 0, false, 0, 0, null);
//
//        List<Product> all = daoInstance.findAll(filter);
//        System.out.println(all);
//        List<String> allUsersByProducts = ProductsUtil.getAllUsersByProducts();
//        System.out.println(allUsersByProducts);
//        List<Product> all = daoInstance.findAll();
//        System.out.println(all);
//        System.out.println(ProductService.getAllProducts());
//        System.out.println(daoInstance.findByPriceAndChangeType(30000, changeTypeDao.findById(2).orElse(null), 1));
    }
}
