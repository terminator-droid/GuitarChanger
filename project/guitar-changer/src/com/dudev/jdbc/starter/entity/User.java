package com.dudev.jdbc.starter.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String address;
    private Role role;
    private String username;
}
