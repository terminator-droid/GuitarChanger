package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.GuitarDao;
import com.dudev.jdbc.starter.dao.PedalDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.dto.PedalDto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.entity.Guitar;
import com.dudev.jdbc.starter.entity.Pedal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {

    private static final ProductService INSTANCE = new ProductService();
    private static final ProductDao productDao = ProductDao.getInstance();

    private static final GuitarDao guitarDao = GuitarDao.getInstance();

    public static final PedalDao pedaldao = PedalDao.getInstance();

    private ProductService() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    public List<ProductDto> getAllProducts() {
        return productDao.findAll().stream()
                .map(product -> new ProductDto(product.getId(), product.getBrand().name(), product.getModel(), product.getPrice()))
                .toList();
    }



    public Dto findById(UUID id) {
        Guitar guitar = guitarDao.findById(id).orElse(null);
        Pedal pedal = pedaldao.findById(id).orElse(null);
        List<Object> products = new ArrayList<>();
        if (guitar != null) {
            return new GuitarDto(guitar.getYear(), guitar.getCountry(), guitar.getPickUps(), guitar.getWood(),guitar.getChangeType().description(), guitar.getChangeWish(), guitar.getChangeValue(), guitar.getDescription());
        } else {
            return new PedalDto(pedal.getDescription(), pedal.getChangeType().description(), pedal.getChangeValue(), pedal.getChangeWish());
        }
    }
    public List<String> getAllUsersByProducts() {
        return productDao.findAll().stream()
                .map(product -> product.getUser().firstName())
                .toList();
    }
}
