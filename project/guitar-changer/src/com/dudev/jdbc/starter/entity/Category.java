package com.dudev.jdbc.starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

    private int id;
    private String name;
}
