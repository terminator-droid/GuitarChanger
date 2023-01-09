package com.dudev.jdbc.starter.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class Guitar {

    private UUID id;
    private String model;
    private String description;
    private int year;
    private String country;
    private String pickUps;
    private String wood;
    private String media;
    private LocalDateTime timestamp;
    private User user;
    private double price;
    private Brand brand;
    private boolean isClosed;
    private ChangeType changeType;
    private double changeValue;
    private String changeWish;
}
