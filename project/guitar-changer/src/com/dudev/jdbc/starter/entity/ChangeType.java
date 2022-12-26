package com.dudev.jdbc.starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeType {

    private int changeType;
    private String description;
}
