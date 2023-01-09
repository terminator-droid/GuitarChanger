package com.dudev.jdbc.starter.validator;

public class PasswordValidator implements Validator<String>{

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final PasswordValidator INSTANCE = new PasswordValidator();
    @Override
    public ValidationResult isValid(String password) {
        ValidationResult validationResult = new ValidationResult();
        if (password.length() < 6) {
            validationResult.add(Error.of("400.password", "password length must be greater than 6"));
        }
        return validationResult;
    }

    public static PasswordValidator getInstance() {
        return INSTANCE;
    }
}
