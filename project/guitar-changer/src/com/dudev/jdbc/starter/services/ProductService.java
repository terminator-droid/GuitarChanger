package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.GuitarDao;
import com.dudev.jdbc.starter.dao.PedalDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dto.*;
import com.dudev.jdbc.starter.entity.Guitar;
import com.dudev.jdbc.starter.entity.Pedal;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.mapper.GuitarMapper;
import com.dudev.jdbc.starter.mapper.PedalMapper;
import com.dudev.jdbc.starter.util.ConnectionManager;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dudev.jdbc.starter.util.ConstantUtil.*;

public class ProductService {

    private static final String IMAGE_FOLDER = "products/";

    private static final ProductService INSTANCE = new ProductService();
    private static final ProductDao productDao = ProductDao.getInstance();

    private static final GuitarDao guitarDao = GuitarDao.getInstance();

    public static final PedalDao pedaldao = PedalDao.getInstance();
    private static final ImageService imageService = ImageService.getInstance();
    private static final GuitarMapper guitarMapper = GuitarMapper.getInstance();
    private static final PedalMapper pedalMapper = PedalMapper.getInstance();
    private static final OffersService offerService = OffersService.getInstance();
    private static final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();

    private ProductService() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }


    public List<ProductDto> getAllProducts() {
        return productDao.findAll().stream()
                .map(product -> new ProductDto(product.getId(), product.getBrand().getName(), product.getModel(), product.getPrice()))
                .toList();
    }

    public List<ProductDto> findProductsByUser(UUID userId, int offset, boolean is_closed) {
        return productDao.findAll(new ProductFilter(10, offset, userId, 0,
                        is_closed, 0, 0, null, 0)).stream()
                .map(product -> new ProductDto(product.getId(), product.getBrand().getName(), product.getModel(), product.getPrice()))
                .toList();
    }

    public Dto findById(UUID id) {
        Guitar guitar = guitarDao.findById(id).orElse(null);
        Pedal pedal = pedaldao.findById(id).orElse(null);
        if (guitar != null) {
            return new GuitarDto(guitar.getId(), guitar.getYear(), guitar.getCountry(), guitar.getPickUps(),
                    guitar.getWood(), guitar.getChangeType().getDescription(),
                    guitar.getChangeWish(), guitar.getChangeValue(),
                    guitar.getDescription(), guitar.getUser().getId(), guitar.getBrand().getName(), guitar.getModel(),
                    guitar.getPrice(), guitar.isClosed(), guitar.getMedia());
        } else if (pedal != null) {
            return new PedalDto(pedal.getId(), pedal.getDescription(), pedal.getChangeType().getDescription(), pedal.getChangeValue(),
                    pedal.getChangeWish(), pedal.getUser().getId(), pedal.getBrand().getName(), pedal.getModel(), pedal.getPrice(), pedal.isClosed(), pedal.getMedia());
        } else {
            return null;
        }
    }

    public List<String> getAllUsersByProducts() {
        return productDao.findAll().stream()
                .map(product -> product.getUser().getFirstName())
                .toList();
    }

    @SneakyThrows
    public void createProduct(Dto product, Part image) {
        if (product instanceof GuitarDto) {
            Guitar guitar = guitarMapper.mapFrom((GuitarDto) product);
            imageService.upload(guitar.getMedia(), image.getInputStream());
            guitarDao.save(guitar);
        } else if (product instanceof PedalDto) {
            Pedal pedal = pedalMapper.mapFrom((PedalDto) product);
            imageService.upload(pedal.getMedia(), image.getInputStream());
            pedaldao.save(pedal);
        }
    }

    @SneakyThrows
    public void deleteProduct(UUID productId) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            productDao.deleteById(productId, connection);
            OffersService.getInstance().deleteByProduct(productId, connection);
//            offerService.deleteByProduct(productId, connection);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<SuitableProductDto> getSuitableProducts(UUID id) {
        Dto product = findById(id);
        List<SuitableProductDto> result = new ArrayList<>();
        int changeType = changeTypeService.changeType(product.getChangeType()).getChangeType();
        if (changeType == EXCHANGE) {
            productDao.findAll(ProductFilter.builder()
                            .changeType(EXCHANGE)
                            .price(product.getPrice())
                            .limit(INITIAL_LIMIT)
                            .build())
                    .stream()
                    .forEach(searchedProduct -> {
                        if (!searchedProduct.getUser().getId().equals(product.getUserId())) {
                            result.add(SuitableProductDto.builder()
                                            .payment(searchedProduct.getPrice() - product.getPrice())
                                            .product(searchedProduct)
                                    .build());
                        }
                    });
        } else if (changeType != SAIL) {
            productDao.findAll(ProductFilter.builder()
                            .changeValue(product.getChangeValue())
                            .limit(INITIAL_LIMIT)
                            .price(changeType == PAY_TO_SELLER
                                    ? product.getPrice() - product.getChangeValue()
                                    : product.getPrice() + product.getChangeValue())
                            .changeType(changeType == PAY_TO_BUYER
                                    ? PAY_TO_SELLER
                                    : PAY_TO_BUYER)
                            .build())
                    .stream()
                    .forEach(searchedProduct -> {
                        if (!searchedProduct.getUser().getId().equals(product.getUserId())) {
                            result.add(SuitableProductDto.builder()
                                    .payment(searchedProduct.getPrice() - product.getPrice())
                                    .product(searchedProduct)
                                    .build());
                        }
                    });
        }
        return result;
    }

}
