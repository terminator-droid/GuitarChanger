package com.dudev.jdbc.starter.dto;

import java.util.UUID;

public record ProductFilter(int limit, int offset, UUID userId, double price, boolean isClosed, int changeType,
                            double changeValue, String changeWish) {
}
