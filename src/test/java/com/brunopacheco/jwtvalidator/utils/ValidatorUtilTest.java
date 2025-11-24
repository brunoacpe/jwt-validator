package com.brunopacheco.jwtvalidator.utils;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUtilTest {

    private ValidatorUtil validatorUtil;

    @BeforeEach
    void setup() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validatorUtil = new ValidatorUtil(validator);
    }

    // -----------------------------------------------------
    // VALID PAYLOAD
    // -----------------------------------------------------

    @Test
    void shouldPassWhenObjectIsValid() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.validAdminFixture();

        assertDoesNotThrow(() -> validatorUtil.validate(dto));
    }

    // -----------------------------------------------------
    // NAME VALIDATION
    // -----------------------------------------------------


    @Test
    void shouldThrowBadRequestWhenNameIsBlank() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.nameBlankFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals(
                "Name cannot be blank", ex.getMessage()
        );
    }

    @Test
    void shouldThrowBadRequestWhenNameHasNumbers() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.nameWithNumbersFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals("Name must contain only letters and spaces", ex.getMessage());
    }

    @Test
    void shouldThrowBadRequestWhenNameIsTooLong() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.nameTooLongFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals("Name must be at most 256 characters", ex.getMessage());
    }

    // -----------------------------------------------------
    // ROLE VALIDATION
    // -----------------------------------------------------

    @Test
    void shouldThrowBadRequestWhenRoleIsNull() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.roleNullFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals("Role cannot be null", ex.getMessage());
    }

    // -----------------------------------------------------
    // SEED VALIDATION
    // -----------------------------------------------------

    @Test
    void shouldThrowBadRequestWhenSeedIsNotNumeric() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.seedNotNumericFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals("Seed must contain only digits", ex.getMessage());
    }

    @Test
    void shouldThrowBadRequestWhenSeedIsBlank() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.seedBlankFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertTrue(
                ex.getMessage().equals("Seed cannot be blank")
                        || ex.getMessage().equals("Seed must contain only digits")
        );
    }

    @Test
    void shouldThrowBadRequestWhenSeedIsNull() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.seedNullFixture();

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );

        assertEquals("Seed cannot be blank", ex.getMessage());
    }

    // -----------------------------------------------------
    // MULTIPLE ERRORS
    // -----------------------------------------------------

    @Test
    void shouldThrowOnlyTheFirstViolationWhenMultipleErrorsExist() {
        JwtPayloadDto dto = Fixtures.JwtPayloadDtoFixtures.multipleInvalidFieldsFixture();

        assertThrows(
                BadRequestException.class,
                () -> validatorUtil.validate(dto)
        );
    }
}
