package com.dudev.jdbc.starter.dto;

import java.util.List;
import java.util.Objects;

public class GuitarDto implements Dto{

    private final int year;
    private final String country;
    private final String pickUps;
    private final String wood;
    private final String changeType;
    private final String changeWish;
    private final double changeValue;
    private final String description;

    public GuitarDto(int year, String country, String pickUps, String wood, String changeType, String changeWish, double changeValue, String description) {
        this.year = year;
        this.country = country;
        this.pickUps = pickUps;
        this.wood = wood;
        this.changeType = changeType;
        this.changeWish = changeWish;
        this.changeValue = changeValue;
        this.description = description;
    }

    public List<Object> getListedFields() {
        return List.of(year, country, pickUps, wood, changeType);
    }

    public String getDescription() {
        return description;
    }

    public String getChangeWish() {
        return changeWish;
    }

    public double getChangeValue() {
        return changeValue;
    }
    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getPickUps() {
        return pickUps;
    }

    public String getWood() {
        return wood;
    }

    public String getChangeType() {
        return changeType;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuitarDto guitarDto = (GuitarDto) o;
        return year == guitarDto.year && Objects.equals(country, guitarDto.country) && Objects.equals(pickUps, guitarDto.pickUps) && Objects.equals(wood, guitarDto.wood) && Objects.equals(changeType, guitarDto.changeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, country, pickUps, wood, changeType);
    }

    @Override
    public String toString() {
        return "GuitarDto{" +
                "year=" + year +
                ", country='" + country + '\'' +
                ", pickUps='" + pickUps + '\'' +
                ", wood='" + wood + '\'' +
                ", changeType='" + changeType + '\'' +
                '}';
    }
}
