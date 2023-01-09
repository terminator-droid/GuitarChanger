package com.dudev.jdbc.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public final class PedalDto implements Dto {

    private final String description;
    private final String changeType;
    private final double changeValue;
    private final String changeWish;

    @Override
    public List<Object> getListedFields() {
        return List.of(description, changeType, changeValue, changeWish);
    }
}

