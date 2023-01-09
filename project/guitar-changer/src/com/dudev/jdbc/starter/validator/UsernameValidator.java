package com.dudev.jdbc.starter.validator;

public class UsernameValidator implements Validator<String> {

    private static final UsernameValidator INSTANCE = new UsernameValidator();
    private static final String PATTERN = "[a-zA-Z_\\-0-9]+";
    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 16;
    @Override
    public ValidationResult isValid(String username) {
        ValidationResult validationResult = new ValidationResult();
        if (!username.matches(PATTERN) && username.length() >= MIN_SIZE && username.length() <= MAX_SIZE) {
            validationResult.add(Error.of("400.username", "length of the field username must be " +
                    "6 to 16 characters long and it can contain letters numbers and signs _-"));
        }
        return validationResult;
    }

    public static UsernameValidator getInstance() {
        return INSTANCE;
    }
}
