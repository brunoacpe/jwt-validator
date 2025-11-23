package com.brunopacheco.jwtvalidator.validators;

public interface Validator<T> {
    void validate(T value);
}
