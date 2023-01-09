package com.dudev.jdbc.starter.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    USER,
    ADMIN;

    public static Optional<Role> find(String role) {
        return Arrays.stream(Role.values())
                .filter(it -> it.name().equals(role))
                .findFirst();
    }
}
