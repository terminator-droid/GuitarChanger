package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.util.PhoneNumberFormatter;

import java.util.UUID;

public class UserMapper implements Mapper<CreateUserDto, User>{

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .username(object.getUsername())
                .address(object.getAddress())
                .firstName(object.getFirstname())
                .lastName(object.getLastName())
                .role(Role.find(object.getRole()).get())
                .password(object.getPassword())
                .phoneNumber(PhoneNumberFormatter.format(object.getPhoneNumber()))
                .build();
    }
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .username(object.getUsername())
                .id(object.getId().toString())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .role(object.getRole().name())
                .address(object.getAddress())
                .password(object.getPassword())
                .phoneNumber(object.getPhoneNumber())
                .build();
    }
    public User mapFrom(UserDto user) {
        return User.builder()
                .id(UUID.fromString(user.getId()))
                .password(user.getPassword())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.find(user.getRole()).orElseThrow())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .address(user.getAddress())
                .build();
    }
    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
