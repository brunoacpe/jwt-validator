package com.brunopacheco.jwtvalidator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("Admin"),
    MEMBER("Member"),
    EXTERNAL("External");

    private final String jwtValue;

    public static RoleEnum fromJwtValue(String value) {
        if (value == null) throw new IllegalArgumentException("Role cannot be null");

        for (RoleEnum role : values()) {
            if (role.jwtValue.equalsIgnoreCase(value.trim()))
                return role;
        }

        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}
