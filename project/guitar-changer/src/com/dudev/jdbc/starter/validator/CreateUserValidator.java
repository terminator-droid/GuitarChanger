package com.dudev.jdbc.starter.validator;

import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.dto.UserFilter;
import com.dudev.jdbc.starter.util.PhoneNumberFormatter;
import com.dudev.jdbc.starter.util.UsernameFormatChecker;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final UserDao userDao = UserDao.getInstance();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (object.getUsername() == null) {
            validationResult.add(Error.of("404.username.null", "username can't be empty"));
        } else if (!UsernameFormatChecker.isValid(object.getUsername())) {
            validationResult.add(Error.of("404.username", "length of the field username must be " +
                    "6 to 16 characters long and it can contain letters numbers and signs _-"));
        } else if (!userDao.findAll(UserFilter.builder()
                .username(object.getUsername())
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.username", "user with such username already exists"));
        }

        if (object.getPhoneNumber() == null) {
            validationResult.add(Error.of("404.phoneNumber.null", "phone number can't be empty"));
        } else if (!PhoneNumberFormatter.isValid(object.getPhoneNumber())) {
            validationResult.add(Error.of("404.phoneNumber", "invalid phone number"));
        } else if (!userDao.findAll(UserFilter.builder()
                .phoneNumber(PhoneNumberFormatter.format(object.getPhoneNumber()))
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.phoneNumber", "user with such phone number already exists"));
        }

        if (object.getPassword() == null) {
            validationResult.add(Error.of("404.password.null", "password can't be empty"));
        } else if (!userDao.findAll(UserFilter.builder()
                .password(object.getPassword())
                .build()).isEmpty()) {
            validationResult.add(Error.of("409.password", "user with such password already exists"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
