package com.dudev.jdbc.starter.dto;

import com.dudev.jdbc.starter.entity.Brand;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.User;
import lombok.Builder;

import java.util.UUID;
@Builder
public record GuitarFilter(int limit, int offset, String model, int year, String country, UUID user, Double price,
                           String brand, boolean isClosed, int changeType,
                           double changeValue, String changeWish) {
}
