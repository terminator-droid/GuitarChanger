package com.dudev.jdbc.starter.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record PedalFilter(String model, UUID userId,
                          double price, String brand, boolean isClosed, int changeType, double changeValue,
                          String changeWish, UUID id) {
}
