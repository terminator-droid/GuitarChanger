package com.dudev.jdbc.starter.validator;

import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.dto.UserFilter;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.util.PhoneNumberFormatter;
import com.dudev.jdbc.starter.util.UsernameFormatChecker;

public class UserValidator implements Validator<CreateUserDto> {

    private static final UserValidator INSTANCE = new UserValidator();
    private static final UserDao userDao = UserDao.getInstance();
    private static final UsernameValidator usernameValidator = UsernameValidator.getInstance();
    private static final PasswordValidator passwordValidator = PasswordValidator.getInstance();


    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        ValidationResult validationResult = new ValidationResult();
        if (userDto.getUsername() == null) {
            validationResult.add(Error.of("404.username.null", "username can't be empty"));
        } else if (!usernameValidator.isValid(userDto.getUsername()).isValid()) {
            validationResult.add(Error.of("400.username", "length of the field username must be " +
                    "6 to 16 characters long and it can contain letters numbers and signs _-"));
        } else if (!userDao.findAll(UserFilter.builder()
                .username(userDto.getUsername())
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.username", "user with such username already exists"));
        }

        if (userDto.getPhoneNumber() == null) {
            validationResult.add(Error.of("404.phoneNumber.null", "phone number can't be empty"));
        } else if (!PhoneNumberFormatter.isValid(userDto.getPhoneNumber())) {
            validationResult.add(Error.of("400.phoneNumber", "invalid phone number"));
        } else if (!userDao.findAll(UserFilter.builder()
                .phoneNumber(PhoneNumberFormatter.format(userDto.getPhoneNumber()))
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.phoneNumber", "user with such phone number already exists"));
        }

        if (userDto.getPassword() == null) {
            validationResult.add(Error.of("404.password.null", "password can't be empty"));
        } else if (!passwordValidator.isValid(userDto.getPassword()).isValid()) {
            validationResult.addAll(passwordValidator.isValid(userDto.getPassword()).getErrors());
        } else if (!userDao.findAll(UserFilter.builder()
                .password(userDto.getPassword())
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.password", "user with such password already exists"));
        }
        return validationResult;
    }

    public static UserValidator getInstance() {
        return INSTANCE;
    }
}
