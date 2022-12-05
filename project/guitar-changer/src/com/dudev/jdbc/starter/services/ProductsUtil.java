package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.ProductDao;

import java.util.List;
import java.util.stream.Collectors;

public final class ProductsUtil {

    private static final ProductDao productDao = ProductDao.getInstance();

    private ProductsUtil() {
    }

    public static String getAllProducts() {
        return productDao.findAllProducts().stream()
                .map(productDto -> String.format("%s, %s, %f", productDto.brand(), productDto.model(), productDto.price()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static List<String> getAllUsersByProducts() {
        return productDao.findAll().stream()
                .map(product -> product.getUser().firstName())
                .toList();
    }
}
