package com.brunopacheco.jwtvalidator.validators;

import org.springframework.stereotype.Component;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;

@Component
public class SeedValidator implements Validator<Integer> {

    @Override
    public void validate(Integer seed) {
        if (!isPrime(seed)) {
            throw new BadRequestException("Seed must be a prime number");
        }
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }
}
