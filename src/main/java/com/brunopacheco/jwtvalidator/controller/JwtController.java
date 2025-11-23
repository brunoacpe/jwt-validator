package com.brunopacheco.jwtvalidator.controller;


import com.brunopacheco.jwtvalidator.controller.dto.request.JwtRequestDto;
import com.brunopacheco.jwtvalidator.logs.Constants;
import com.brunopacheco.jwtvalidator.service.JwtValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.argument.StructuredArgument;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/validate")
@RequiredArgsConstructor
public class JwtController {

    private final JwtValidationService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtController.class);
    private static final String API_POST_JWT_CONTROLLER_EVENT_START = "JWT validation POST api has been called.";

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/jwt"
    )
    public ResponseEntity<Object> validateJwtApi(
            @Valid @RequestBody JwtRequestDto requestBody
            ) {
        LOGGER.info(
                API_POST_JWT_CONTROLLER_EVENT_START,
                StructuredArguments.kv(Constants.LogKeys.REQUEST_BODY_LOG_KEY, requestBody)
        );

        return ResponseEntity.ok("Hello World!");
    }
}
