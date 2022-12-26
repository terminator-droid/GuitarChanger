package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.GuitarDao;
import com.dudev.jdbc.starter.dao.PedalDao;
import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.*;
import com.dudev.jdbc.starter.entity.Pedal;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.exception.ValidationException;
import com.dudev.jdbc.starter.mapper.CreateUserMapper;
import com.dudev.jdbc.starter.mapper.GetUserMapper;
import com.dudev.jdbc.starter.util.ConnectionManager;
import com.dudev.jdbc.starter.validator.CreateUserValidator;
import com.dudev.jdbc.starter.validator.ValidationResult;
import lombok.NoArgsConstructor;
import org.apache.taglibs.standard.lang.jstl.test.beans.Factory;

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

    private final CreateUserValidator userValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper userMapper = CreateUserMapper.getInstance();
    private static final GetUserMapper getUserMapper = GetUserMapper.getInstance();

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

    public Optional<UserDto> login(String username, String password) {
        return userDao.findAll(UserFilter.builder()
                .password(password)
                .username(username)
                .build()).stream().findFirst().map(getUserMapper::mapFrom);
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
