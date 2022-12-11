package com.dudev.jdbc.starter.dto;

import java.util.List;
import java.util.Objects;

public final class PedalDto implements Dto {

    private final String description;
    private final String changeType;
    private final double changeValue;
    private final String changeWish;

    public PedalDto(String description, String changeType, double changeValue, String changeWish) {
        this.description = description;
        this.changeType = changeType;
        this.changeValue = changeValue;
        this.changeWish = changeWish;
    }

    @Override
    public List<Object> getListedFields() {
        return List.of(description, changeType, changeValue, changeWish);
    }

    public String getDescription() {
        return description;
    }

    public String getChangeType() {
        return changeType;
    }

    public double getChangeValue() {
        return changeValue;
    }

    public String getChangeWish() {
        return changeWish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedalDto pedalDto = (PedalDto) o;
        return Objects.equals(description, pedalDto.description) && Objects.equals(changeType, pedalDto.changeType) && Objects.equals(changeValue, pedalDto.changeValue) && Objects.equals(changeWish, pedalDto.changeWish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, changeType, changeValue, changeWish);
    }

    @Override
    public String toString() {
        return "PedalDto{" +
                "description='" + description + '\'' +
                ", changeType='" + changeType + '\'' +
                ", changeValue='" + changeValue + '\'' +
                ", changeWish='" + changeWish + '\'' +
                '}';
    }
}

