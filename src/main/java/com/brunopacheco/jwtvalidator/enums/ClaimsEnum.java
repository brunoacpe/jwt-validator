package com.brunopacheco.jwtvalidator.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
public enum ClaimsEnum {
    NAME("Name"),
    ROLE("Role"),
    SEED("Seed");

    private final String key;

    ClaimsEnum(String key) {
        this.key = key;
    }


    public static Set<String> validKeys() {
        return Arrays.stream(values())
                .map(ClaimsEnum::getKey)
                .collect(Collectors.toSet());
    }
}
