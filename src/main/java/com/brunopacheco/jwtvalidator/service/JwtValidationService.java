package com.brunopacheco.jwtvalidator.service;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;
import com.brunopacheco.jwtvalidator.utils.BooleanTranslator;
import com.brunopacheco.jwtvalidator.utils.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidationService implements JwtValidationUseCase {

    private final JwtParser jwtParser;

    @Override
    public String validateJwt(String requestDto) {
        JwtPayloadDto payloadDto = jwtParser.parse(requestDto);
        return BooleanTranslator.booleanToPortuguese(Boolean.TRUE);
    }
}
