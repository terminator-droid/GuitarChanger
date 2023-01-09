package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.entity.Product;

public class ProductMapper implements Mapper<Product, ProductDto>{

    private static final ProductMapper INSTANCE = new ProductMapper();

    public static ProductMapper getInstance(){
        return INSTANCE;
    }
    @Override
    public ProductDto mapFrom(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .brand(product.getBrand().getName())
                .price(product.getPrice())
                .model(product.getModel())
                .build();
    }
}
