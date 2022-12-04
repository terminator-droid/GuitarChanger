package com.dudev.jdbc.starter.entity;

public enum Role {
    USER,
    ADMIN;

    public static Role getRoleFromString(String role) {
        if (role.equals("user")) {
            return USER;
        } else {
            return ADMIN;
        }
    }
}
