package com.dudev.jdbc.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private UUID id;
    private String brand;
    private String model;
    private double price;
}
