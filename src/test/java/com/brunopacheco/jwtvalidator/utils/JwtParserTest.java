package com.brunopacheco.jwtvalidator.utils;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.enums.RoleEnum;
import com.brunopacheco.jwtvalidator.exception.InvalidJwtException;
import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtParserTest {

    private JwtParser parser;

    @BeforeEach
    void setup() {
        parser = new JwtParser();
    }

    @Test
    void shouldParseValidJwt() {
        JwtPayloadDto dto = parser.parse(Fixtures.VALID);

        assertEquals("Toninho Araujo", dto.name());
        assertEquals(RoleEnum.ADMIN, dto.role());
        assertEquals("7841", dto.seed());
    }

    @Test
    void shouldRejectTokenWithMoreThanThreeClaims() {
        InvalidJwtException ex = assertThrows(
                InvalidJwtException.class,
                () -> parser.parse(Fixtures.EXTRA_CLAIM)
        );

        assertEquals("Token contains invalid or unexpected claims", ex.getMessage());
    }

    @Test
    void shouldRejectTokenWithMissingClaim() {
        InvalidJwtException ex = assertThrows(
                InvalidJwtException.class,
                () -> parser.parse(Fixtures.MISSING_ROLE)
        );

        assertEquals("Token is missing required claims", ex.getMessage());
    }

    @Test
    void shouldRejectTokenWithNullClaimValue() {
        InvalidJwtException ex = assertThrows(
                InvalidJwtException.class,
                () -> parser.parse(Fixtures.CLAIM_NULL)
        );

        assertEquals("Missing required claims", ex.getMessage());
    }

    @Test
    void shouldRejectTokenWithInvalidRole() {
        InvalidJwtException ex = assertThrows(
                InvalidJwtException.class,
                () -> parser.parse(Fixtures.INVALID_ROLE)
        );

        assertTrue(ex.getMessage().contains("Malformed or invalid JWT"));
    }

    @Test
    void shouldAcceptClaimsInDifferentOrder() {
        JwtPayloadDto dto = parser.parse(Fixtures.ORDER_DIFFERENT);

        assertEquals("Maria", dto.name());
        assertEquals(RoleEnum.MEMBER, dto.role());
        assertEquals("1117", dto.seed());
    }

    @Test
    void shouldThrowWhenTokenIsMalformed() {
        InvalidJwtException ex = assertThrows(
                InvalidJwtException.class,
                () -> parser.parse(Fixtures.INVALID_JWT_TOKEN)
        );

        assertTrue(ex.getMessage().startsWith("Malformed or invalid JWT"));
    }
}
