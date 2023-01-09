package com.dudev.jdbc.starter.dto;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
    @AllArgsConstructor
public class GuitarDto implements Dto{

    private final UUID id;
    private final int year;
    private final String country;
    private final String pickUps;
    private final String wood;
    private final String changeType;
    private final String changeWish;
    private final double changeValue;
    private final String description;
    private final UUID userId;
    private final String brand;
    private final String model;
    private final double price;
    private final boolean isClosed;
    private final String media;
    public List<Object> getListedFields() {
        return List.of(year, country, pickUps, wood, changeType);
    }
}
