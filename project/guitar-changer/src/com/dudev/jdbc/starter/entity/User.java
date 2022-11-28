package com.dudev.jdbc.starter.entity;


import java.util.UUID;

public record User(UUID id, String firstName, String lastName, String phoneNumber, String password, String address,
                   Role role) {
}
