package com.brunopacheco.jwtvalidator.fixtures;

public class Fixtures {
    public static final String POST_API_PATH = "/api/v1/validate/jwt";
    public static final String VALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiVG9uaW5obyBBcmF1am8iLCJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSJ9.2sy5IzDmC_1oGHd5Jq9sSHH8Bt6NzfZhV1Bu5-WiBmA";
    public static final String INVALID_JWT_TOKEN = "abc.def.ghi";
    public static final String VALID_TOKEN_BOOLEAN_RESPONSE = "verdadeiro";
    public static final String INVALID_TOKEN_BOOLEAN_RESPONSE = "falso";
}
