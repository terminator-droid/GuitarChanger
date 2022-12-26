package com.dudev.jdbc.starter.util;

import com.oracle.wls.shaded.org.apache.regexp.RE;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class PhoneNumberFormatter {

    //language=RegExp
    private static final String REGEX = "\\+?(\\d+) ?\\(?(\\d{3})\\)? ?(\\d{3})[- ]?(\\d{2})[- ]?(\\d{2})";
    private static final String PATTERN = "+7 ($2) $3-$4-$5";

    public static String format(String phoneNumber) {
        return phoneNumber.replaceAll(REGEX, PATTERN);
    }

    public boolean isValid(String phoneNumber) {
        return Pattern.matches(REGEX, phoneNumber);
    }
}
