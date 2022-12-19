package com.dudev.jdbc.starter.dto;

import java.util.UUID;

public record ProductDto (UUID id,  String brand, String model, double price){
}
