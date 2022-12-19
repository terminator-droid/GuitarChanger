package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.dto.UserShortDTO;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.StringValueHandler;

import java.util.List;
import java.util.UUID;

public final class UserService {

    private static final UserService INSTANCE = new UserService();

    private static final UserDao userDao = UserDao.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<UserDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(user -> new UserDto(user.username(), user.firstName(), user.lastName(), user.role().name()))
                .toList();
    }

    public void addUser(String firstName, String lastName, String phoneNumber, String password,
                        String address, String role, String username) {
        userDao.save(new User(firstName, lastName, phoneNumber, password, address, Role.valueOf(role), username));
    }

    public UserDto findById(UUID id) {
        User user = userDao.findById(id).orElse(null);
        return new UserDto(user.username(), user.firstName(), user.lastName(), user.role().name());
    }
}
