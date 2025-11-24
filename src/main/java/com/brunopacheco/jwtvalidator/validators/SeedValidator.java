package com.brunopacheco.jwtvalidator.validators;

import org.springframework.stereotype.Component;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;

@Component
public class SeedValidator implements Validator<String> {

    @Override
    public void validate(String seed) {
        int convertedSeed = Integer.parseInt(seed);

        if (!isPrime(convertedSeed)) {
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
