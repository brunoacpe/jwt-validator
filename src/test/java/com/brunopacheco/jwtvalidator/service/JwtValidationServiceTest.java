package com.brunopacheco.jwtvalidator.service;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.enums.RoleEnum;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import com.brunopacheco.jwtvalidator.util.JwtParser;
import com.brunopacheco.jwtvalidator.util.ValidatorUtil;
import com.brunopacheco.jwtvalidator.validators.SeedValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtValidationServiceTest {

    @Mock
    private JwtParser jwtParser;

    @Mock
    private ValidatorUtil validatorUtil;

    @Mock
    private SeedValidator seedValidator;

    @InjectMocks
    private JwtValidationService service;

    @Test
    void shouldValidateJwtSuccessfully() {
        String jwt = Fixtures.VALID_JWT_TOKEN;

        JwtPayloadDto payload = Fixtures.JwtPayloadDtoFixtures.validJwtPayloadDtoFixture();

        when(jwtParser.parse(jwt)).thenReturn(payload);

        String result = service.validateJwt(jwt);

        assertEquals(Fixtures.VALID_TOKEN_BOOLEAN_RESPONSE, result);

        verify(jwtParser).parse(jwt);
        verify(validatorUtil).validate(payload);
        verify(seedValidator).validate("7841");
    }

    @Test
    void shouldThrowExceptionWhenParserFails() {
        when(jwtParser.parse(Fixtures.INVALID_JWT_TOKEN)).thenThrow(new BadRequestException("invalid token"));

        BadRequestException ex =
                assertThrows(BadRequestException.class, () -> service.validateJwt(Fixtures.INVALID_JWT_TOKEN));

        assertEquals("invalid token", ex.getMessage());

        verify(jwtParser).parse(Fixtures.INVALID_JWT_TOKEN);
        verifyNoInteractions(validatorUtil);
        verifyNoInteractions(seedValidator);
    }

    @Test
    void shouldThrowExceptionWhenValidatorUtilFails() {
        String jwt = Fixtures.VALID_JWT_TOKEN;

        JwtPayloadDto payload = new JwtPayloadDto("Name", RoleEnum.ADMIN, "11");

        when(jwtParser.parse(jwt)).thenReturn(payload);
        doThrow(new BadRequestException("jakarta failed"))
                .when(validatorUtil).validate(payload);

        BadRequestException ex =
                assertThrows(BadRequestException.class, () -> service.validateJwt(jwt));

        assertEquals("jakarta failed", ex.getMessage());

        verify(jwtParser).parse(jwt);
        verify(validatorUtil).validate(payload);
        verifyNoInteractions(seedValidator);
    }

    @Test
    void shouldThrowExceptionWhenSeedValidatorFails() {
        String jwt = Fixtures.VALID_JWT_TOKEN;

        JwtPayloadDto payload = Fixtures.JwtPayloadDtoFixtures.invalidJwtPayloadDtoSeedIsNotAPrimeNumberFixture();

        when(jwtParser.parse(jwt)).thenReturn(payload);
        doThrow(new BadRequestException("not prime"))
                .when(seedValidator).validate("10");

        BadRequestException ex =
                assertThrows(BadRequestException.class, () -> service.validateJwt(jwt));

        assertEquals("not prime", ex.getMessage());

        verify(jwtParser).parse(jwt);
        verify(validatorUtil).validate(payload);
        verify(seedValidator).validate("10");
    }
}
