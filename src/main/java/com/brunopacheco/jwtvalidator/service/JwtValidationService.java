package com.brunopacheco.jwtvalidator.service;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;
import com.brunopacheco.jwtvalidator.utils.BooleanTranslator;
import com.brunopacheco.jwtvalidator.utils.JwtParser;
import com.brunopacheco.jwtvalidator.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidationService implements JwtValidationUseCase {

    private final JwtParser jwtParser;
    private final ValidatorUtil validatorUtil;

    @Override
    public String validateJwt(String requestDto) {
        JwtPayloadDto payloadDto = jwtParser.parse(requestDto);

        validatorUtil.validate(payloadDto);

        return BooleanTranslator.booleanToPortuguese(Boolean.TRUE);
    }
}
