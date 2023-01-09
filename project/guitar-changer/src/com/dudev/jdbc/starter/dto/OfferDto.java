package com.dudev.jdbc.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OfferDto {

    private final UUID id;
    private final UUID buyer;
    private final UUID interchange;
    private final String changeType;
    private final double changeValue;
    private final LocalDateTime timestamp;
    @Setter
    private String interchangeModel;
    @Setter
    private String interchangeBrand;
    private boolean accepted;
    private String buyerName;

}
