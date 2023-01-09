package com.dudev.jdbc.starter.validator;

public interface Validator <T> {

    ValidationResult isValid(T object);

}
