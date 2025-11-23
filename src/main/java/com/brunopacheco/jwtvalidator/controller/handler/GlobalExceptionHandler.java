package com.brunopacheco.jwtvalidator.controller.handler;

import com.brunopacheco.jwtvalidator.exception.InvalidJwtException;
import com.brunopacheco.jwtvalidator.logs.Constants;
import com.brunopacheco.jwtvalidator.utils.BooleanTranslator;
import net.logstash.logback.argument.StructuredArguments;
import com.brunopacheco.jwtvalidator.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String EXCEPTION_HANDLER_ERROR_MESSAGE = "Validation failed.";
    /**
     * Qualquer erro da aplicação deve resultar em "falso".
     * O desafio não pede status HTTP customizado.
     */
    private ResponseEntity<String> falseResponse(Exception ex) {

        LOGGER.error(EXCEPTION_HANDLER_ERROR_MESSAGE,
                StructuredArguments.kv(Constants.LogKeys.EXCEPTION_MESSAGE_LOG_KEY, ex.getMessage())
        );

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BooleanTranslator.booleanToPortuguese(Boolean.FALSE));
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<String> handleInvalidJwt(InvalidJwtException ex) {
        return falseResponse(ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return falseResponse(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleJakartaValidation(MethodArgumentNotValidException ex) {
        return falseResponse(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return falseResponse(ex);
    }
}
