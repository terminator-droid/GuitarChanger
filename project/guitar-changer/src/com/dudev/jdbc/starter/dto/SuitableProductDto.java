package com.dudev.jdbc.starter.dto;

import com.dudev.jdbc.starter.entity.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuitableProductDto {

    private final Product product;
    private final double payment;

}
