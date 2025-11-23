package com.brunopacheco.jwtvalidator.controller;

import com.brunopacheco.jwtvalidator.controller.handler.GlobalExceptionHandler;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import com.brunopacheco.jwtvalidator.exception.InvalidJwtException;
import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void shouldReturnFalsoForInvalidJwtException() {
        InvalidJwtException ex = new InvalidJwtException("Invalid token");
        ResponseEntity<String> response = handler.handleInvalidJwt(ex);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE, response.getBody());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
    }

    @Test
    void shouldReturnFalsoForBadRequestException() {
        BadRequestException ex = new BadRequestException("Bad request");
        ResponseEntity<String> response = handler.handleBadRequest(ex);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE, response.getBody());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
    }

    @Test
    void shouldReturnFalsoForMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);

        ResponseEntity<String> response = handler.handleJakartaValidation(ex);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE, response.getBody());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
    }

    @Test
    void shouldReturnFalsoForGenericException() {
        Exception ex = new RuntimeException("Unexpected error");
        ResponseEntity<String> response = handler.handleGeneric(ex);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE, response.getBody());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
    }
}