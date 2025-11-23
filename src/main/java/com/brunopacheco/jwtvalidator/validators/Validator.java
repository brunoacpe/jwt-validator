package com.brunopacheco.jwtvalidator.validators;

public interface Validator<T> {
    boolean validate(T value);
}
