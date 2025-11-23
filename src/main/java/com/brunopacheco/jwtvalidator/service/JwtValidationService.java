package com.brunopacheco.jwtvalidator.service;

import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;
import com.brunopacheco.jwtvalidator.utils.BooleanTranslator;
import org.springframework.stereotype.Service;

@Service
public class JwtValidationService implements JwtValidationUseCase {
    @Override
    public String validateJwt(String requestDto) {
        return BooleanTranslator.booleanToPortuguese(Boolean.TRUE);
    }
}
