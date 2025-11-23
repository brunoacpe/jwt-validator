package com.brunopacheco.jwtvalidator.service;

import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;
import com.brunopacheco.jwtvalidator.utils.BooleanTranslator;
import com.brunopacheco.jwtvalidator.utils.JwtParser;
import com.brunopacheco.jwtvalidator.utils.ValidatorUtil;
import com.brunopacheco.jwtvalidator.validators.SeedValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidationService implements JwtValidationUseCase {

    private final JwtParser jwtParser;
    private final ValidatorUtil validatorUtil;
    private final SeedValidator seedValidator;

    @Override
    public String validateJwt(String requestDto) {
        JwtPayloadDto payloadDto = jwtParser.parse(requestDto);

        validatorUtil.validate(payloadDto);
        seedValidator.validate(payloadDto.seed());

        return BooleanTranslator.booleanToPortuguese(Boolean.TRUE);
    }
}
