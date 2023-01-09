package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.util.PhoneNumberFormatter;

public class CreateUserMapper implements Mapper<CreateUserDto, User>{

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

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

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
