package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.entity.Guitar;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.services.BrandService;
import com.dudev.jdbc.starter.services.ChangeTypeService;
import com.dudev.jdbc.starter.services.UserService;

import java.time.LocalDateTime;

public class GuitarMapper implements Mapper<GuitarDto, Guitar>{

    private static final GuitarMapper INSTANCE = new GuitarMapper();
    private static final String IMAGE_FOLDER = "products/guitars/";
    private static final UserService userService = UserService.getInstance();
    private static final UserMapper userMapper = UserMapper.getInstance();
    private static final BrandService brandService = BrandService.getInstance();
    private static final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();
    @Override
    public Guitar mapFrom(GuitarDto object) {
        return Guitar.builder()
                .media(IMAGE_FOLDER + object.getMedia())
                .price(object.getPrice())
                .wood(object.getWood())
                .year(object.getYear())
                .model(object.getModel())
                .user(userMapper.mapFrom(userService.findById(object.getUserId())))
                .pickUps(object.getPickUps())
                .brand(brandService.getBrand(Integer.parseInt(object.getBrand())))
                .timestamp(LocalDateTime.now())
                .changeType(changeTypeService.getChangeType(Integer.parseInt(object.getChangeType())))
                .country(object.getCountry())
                .description(object.getDescription())
                .changeValue(object.getChangeValue())
                .changeWish(object.getChangeWish())
                .build();
    }

    public static GuitarMapper getInstance() {
        return INSTANCE;
    }
}
