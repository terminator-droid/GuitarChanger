package com.dudev.jdbc.starter.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Offer {

    private UUID id;
    private User buyer;
    private Product interchange;
    private ChangeType changeType;
    private double changeValue;
    private LocalDateTime timestamp;
    private boolean accepted;
}
