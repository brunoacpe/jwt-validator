package com.brunopacheco.jwtvalidator.controller.dto.request;

import jakarta.validation.constraints.NotBlank;


public record JwtRequestDto(
        @NotBlank
        String token
) {
}
