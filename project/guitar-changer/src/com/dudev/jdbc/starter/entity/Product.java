package com.dudev.jdbc.starter.entity;

import com.dudev.jdbc.starter.dao.BrandDao;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.lang.invoke.StringConcatException;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Product {

    private UUID id;
    private LocalDateTime timestamp;
    private User user;
    private Double price;
    private boolean isClosed;
    private ChangeType changeType;
    private double changeValue;
    private String changeWish;
    private Brand brand;
    private String model;
    private String description;
}
