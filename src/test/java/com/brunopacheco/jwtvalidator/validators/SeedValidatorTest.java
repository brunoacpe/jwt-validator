package com.brunopacheco.jwtvalidator.validators;

import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeedValidatorTest {

    private SeedValidator seedValidator;

    @BeforeEach
    void setup() {
        seedValidator = new SeedValidator();
    }


    @Test
    void shouldPassWhenSeedIsPrime() {
        assertDoesNotThrow(() -> seedValidator.validate("11"));
    }

    @Test
    void shouldPassWhenSeedIsAnotherPrime() {
        assertDoesNotThrow(() -> seedValidator.validate("7879"));
    }


    @Test
    void shouldThrowWhenSeedIsNotPrime() {
        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> seedValidator.validate("10")
        );

        assertEquals("Seed must be a prime number", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSeedIsOne() {
        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> seedValidator.validate("1")
        );

        assertEquals("Seed must be a prime number", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSeedIsZero() {
        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> seedValidator.validate("0")
        );

        assertEquals("Seed must be a prime number", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSeedIsNegative() {
        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> seedValidator.validate("-7")
        );

        assertEquals("Seed must be a prime number", ex.getMessage());
    }


    @Test
    void shouldThrowWhenSeedIsNotNumeric() {
        NumberFormatException ex = assertThrows(
                NumberFormatException.class,
                () -> seedValidator.validate("abc")
        );

        assertTrue(ex.getMessage().contains("For input string"));
    }

    @Test
    void shouldThrowWhenSeedIsEmpty() {
        NumberFormatException ex = assertThrows(
                NumberFormatException.class,
                () -> seedValidator.validate("")
        );

        assertTrue(ex.getMessage().contains("For input string"));
    }

    @Test
    void shouldThrowWhenSeedIsNull() {
        assertThrows(
                NumberFormatException.class,
                () -> seedValidator.validate(null)
        );
    }
}
