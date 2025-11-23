package com.brunopacheco.jwtvalidator.validators;

import org.springframework.stereotype.Component;

@Component
public class RoleValidator implements Validator<String>{
    @Override
    public boolean validate(String value) {
        return false;
    }
}
