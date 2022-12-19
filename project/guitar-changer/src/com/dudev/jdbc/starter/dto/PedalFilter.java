package com.dudev.jdbc.starter.dto;

import java.util.UUID;

public record PedalFilter(String model, UUID userId,
                          double price, String brand, boolean isClosed, int changeType, double changeValue,
                          String changeWish) {
}
