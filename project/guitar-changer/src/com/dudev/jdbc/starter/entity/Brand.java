package com.dudev.jdbc.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Brand {

    private final int id;
    private final String name;
    private final Category category;
}
