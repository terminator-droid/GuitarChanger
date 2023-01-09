package com.dudev.jdbc.starter.dto;

import com.dudev.jdbc.starter.entity.Brand;

import java.util.List;
import java.util.UUID;

public interface Dto {

    List<Object> getListedFields();
    UUID getId();
    UUID getUserId();
    String getModel();
    String getBrand();
    boolean isClosed();
    double getPrice();
    String getChangeType();
    double getChangeValue();
}
