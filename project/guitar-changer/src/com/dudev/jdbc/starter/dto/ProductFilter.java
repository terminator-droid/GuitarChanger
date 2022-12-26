package com.dudev.jdbc.starter.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record ProductFilter(int limit, int offset, UUID userId, double price, boolean isClosed, int changeType,
                            double changeValue, String changeWish, int brand) {
}
