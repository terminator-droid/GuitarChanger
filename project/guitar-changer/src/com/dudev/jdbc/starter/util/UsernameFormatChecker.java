package com.dudev.jdbc.starter.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;
@UtilityClass
public class UsernameFormatChecker {

    private static final String PATTERN = "[a-zA-Z_\\-0-9]+";
    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 16;

    public boolean isValid(String username) {
        return username.matches(PATTERN) && username.length() >= MIN_SIZE && username.length() <= MAX_SIZE;
    }
}
