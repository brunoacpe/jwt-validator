package com.brunopacheco.jwtvalidator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record JwtPayloadDto(

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 256, message = "Name must be at most 256 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$",
                message = "Name must contain only letters and spaces"
        )
        String name,

        @NotBlank(message = "Role cannot be blank")
        @Pattern(
                regexp = "Admin|Member|External",
                message = "Role must be one of: Admin, Member, External"
        )
        String role,

        @NotBlank(message = "Seed cannot be blank")
        @Pattern(
                regexp = "^[0-9]+$",
                message = "Seed must contain only digits"
        )
        String seed

) {}
