package com.dudev.jdbc.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;
@Data
@Builder
@AllArgsConstructor
public class GuitarDto implements Dto{

    private final int year;
    private final String country;
    private final String pickUps;
    private final String wood;
    private final String changeType;
    private final String changeWish;
    private final double changeValue;
    private final String description;


    public List<Object> getListedFields() {
        return List.of(year, country, pickUps, wood, changeType);
    }
}
