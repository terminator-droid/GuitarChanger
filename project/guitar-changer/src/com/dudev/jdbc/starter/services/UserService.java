package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.GuitarDao;
import com.dudev.jdbc.starter.dao.PedalDao;
import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.*;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.ValidationException;
import com.dudev.jdbc.starter.mapper.UserMapper;
import com.dudev.jdbc.starter.util.ConnectionManager;
import com.dudev.jdbc.starter.validator.Error;
import com.dudev.jdbc.starter.validator.UserValidator;
import com.dudev.jdbc.starter.validator.ValidationResult;
import com.oracle.wls.shaded.org.apache.bcel.generic.ARETURN;
import jakarta.servlet.annotation.ServletSecurity;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@NoArgsConstructor
public final class UserService {

    private static final UserService INSTANCE = new UserService();

    private static final UserDao userDao = UserDao.getInstance();
    private static final GuitarDao guitarDao = GuitarDao.getInstance();
    private static final PedalDao pedalDao = PedalDao.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private final UserValidator userValidator = UserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public List<UserDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(user -> UserDto.builder()
                        .id(user.getId().toString())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .role(user.getRole().name())
                        .username(user.getUsername())
                        .build())
                .toList();
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        return userDao.findAll(UserFilter.builder()
                        .password(currentPassword)
                        .build()).stream().findFirst()
                .map(user -> {
                    userDao.updatePassword(user.getId().toString(), newPassword);
                    return user;
                })
                .isPresent();

    }

    public Optional<UserDto> login(String username, String password) {
        return username == null || password == null
                ? Optional.empty()
                : userDao.findAll(UserFilter.builder()
                .password(password)
                .username(username)
                .build()).stream().findFirst().map(userMapper::mapFrom);
    }

    public UserDto deleteUser(UUID id) throws SQLException {
        Connection connection = null;
        UserDto deletedUser = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            guitarDao.delete(GuitarFilter.builder()
                    .user(id)
                    .build(), connection);
            pedalDao.delete(PedalFilter.builder()
                    .userId(id)
                    .build(), connection);
            User user = userDao.delete(id, connection);
            deletedUser = UserDto.builder()
                    .username(user.getUsername())
                    .build();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null) {
                connection.close();
            }
        }
        return deletedUser;
    }

    public void createUser(CreateUserDto userDto) {
        ValidationResult validationResult = userValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = userMapper.mapFrom(userDto);
        userDao.save(user);
    }

    public UserDto findById(UUID id) {
        User user = userDao.findById(id).orElseThrow();
        return UserDto.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .id(user.getId().toString())
                .build();
    }
}
