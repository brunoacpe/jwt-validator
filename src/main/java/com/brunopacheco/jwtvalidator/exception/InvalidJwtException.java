package com.brunopacheco.jwtvalidator.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message) {
        super(message);
    }
}
