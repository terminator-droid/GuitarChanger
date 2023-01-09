package com.dudev.jdbc.starter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String password;
    private String address;
    private String phoneNumber;

}
