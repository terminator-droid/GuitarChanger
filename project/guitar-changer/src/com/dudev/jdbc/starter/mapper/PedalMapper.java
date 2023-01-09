package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.PedalDto;
import com.dudev.jdbc.starter.entity.Pedal;
import com.dudev.jdbc.starter.services.BrandService;
import com.dudev.jdbc.starter.services.ChangeTypeService;
import com.dudev.jdbc.starter.services.UserService;

import java.time.LocalDateTime;

public class PedalMapper implements Mapper<PedalDto, Pedal>{

    private static final PedalMapper INSTANCE = new PedalMapper();
    private static final String IMAGE_FOLDER = "products/pedals/";
    private static final UserService userService = UserService.getInstance();
    private static final UserMapper userMapper = UserMapper.getInstance();
    private static final BrandService brandService = BrandService.getInstance();
    private static final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();

    @Override
    public Pedal mapFrom(PedalDto object) {
        return Pedal.builder()
                .media(IMAGE_FOLDER + object.getMedia())
                .price(object.getPrice())
                .model(object.getModel())
                .user(userMapper.mapFrom(userService.findById(object.getUserId())))
                .brand(brandService.getBrand(Integer.parseInt(object.getBrand())))
                .timestamp(LocalDateTime.now())
                .changeType(changeTypeService.getChangeType(Integer.parseInt(object.getChangeType())))
                .description(object.getDescription())
                .changeValue(object.getChangeValue())
                .changeWish(object.getChangeWish())
                .build();
    }
    public static PedalMapper getInstance() {
        return INSTANCE;
    }
}
