package com.brunopacheco.jwtvalidator.controller;



import com.brunopacheco.jwtvalidator.logs.Constants;

import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;

import lombok.RequiredArgsConstructor;

import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/validate")
@RequiredArgsConstructor
public class JwtController {

    private final JwtValidationUseCase useCase;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtController.class);
    private static final String API_POST_JWT_CONTROLLER_EVENT_START = "JWT validation POST api has been called.";
    private static final String API_POST_JWT_CONTROLLER_EVENT_END = "JWT validation POST api end.";

    @PostMapping(
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE,
            path = "/jwt"
    )
    public ResponseEntity<String> validateJwtApi(
            @RequestBody String requestBody
            ) {
        LOGGER.info(
                API_POST_JWT_CONTROLLER_EVENT_START,
                StructuredArguments.kv(Constants.LogKeys.REQUEST_BODY_LOG_KEY, requestBody)
        );
        String useCaseOutput = useCase.validateJwt(requestBody);

        LOGGER.info(
                API_POST_JWT_CONTROLLER_EVENT_END,
                StructuredArguments.kv(Constants.LogKeys.RESPONSE_BODY_LOG_KEY, useCaseOutput)
        );

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(useCaseOutput);
    }
}
