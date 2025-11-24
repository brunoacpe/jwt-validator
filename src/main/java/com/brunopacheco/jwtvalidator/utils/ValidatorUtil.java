package com.brunopacheco.jwtvalidator.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class ValidatorUtil {
    private final Validator validator;

    public <T> void validate(T object) {

        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            String message = violations
                    .iterator()
                    .next()
                    .getMessage();

            throw new BadRequestException(message);
        }
    }
}

