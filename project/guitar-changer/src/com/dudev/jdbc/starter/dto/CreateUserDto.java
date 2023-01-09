package com.dudev.jdbc.starter.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {

    String firstname;
    String lastName;
    String username;
    String phoneNumber;
    String password;
    String address;
    String role;
}
