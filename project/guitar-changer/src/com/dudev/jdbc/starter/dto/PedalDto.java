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
public final class PedalDto implements Dto {

    private final UUID id;
    private final String description;
    private final String changeType;
    private final double changeValue;
    private final String changeWish;
    private final UUID userId;
    private final String brand;
    private final String model;
    private final double price;
    private final boolean isClosed;
    private final String media;

    @Override
    public List<Object> getListedFields() {
        return List.of(description, changeType, changeValue, changeWish);
    }
}

