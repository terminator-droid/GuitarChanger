package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.User;

public class GetUserMapper implements Mapper<User, UserDto> {

    private static final GetUserMapper INSTSNCE = new GetUserMapper();
    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .username(object.getUsername())
                .id(object.getId().toString())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .role(object.getRole().name())
                .build();
    }
    public static GetUserMapper getInstance() {
        return INSTSNCE;
    }
}
