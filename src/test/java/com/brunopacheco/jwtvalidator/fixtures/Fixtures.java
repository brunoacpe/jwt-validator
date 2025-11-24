package com.brunopacheco.jwtvalidator.fixtures;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.enums.RoleEnum;

public class Fixtures {
    public static final String POST_API_PATH = "/api/v1/validate/jwt";
    public static final String VALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiVG9uaW5obyBBcmF1am8iLCJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSJ9.2sy5IzDmC_1oGHd5Jq9sSHH8Bt6NzfZhV1Bu5-WiBmA";
    public static final String INVALID_JWT_TOKEN = "abc.def.ghi";
    public static final String VALID_TOKEN_BOOLEAN_RESPONSE = "verdadeiro";
    public static final String INVALID_TOKEN_BOOLEAN_RESPONSE = "falso";
    public static final String INVALID_JWT_TOKEN_WITH_MORE_THAN_3_CLAIMS = "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiVG9uaW5obyIsIlJvbGUiOiJBZG1pbiIsIlNlZWQiOiI3ODQxIiwiRXh0cmEiOiJ4In0.qGQ_SxE3kZr9V9suYQ1kS2HPyPzHeVrzYJ2gDIh5xp4";
    public static final String INVALID_JWT_TOKEN_NULL_CLAIM_VALUE = "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiVG9uaW5obyIsIlJvbGUiOiJBZG1pbiIsIlNlZWQiOm51bGx9.eJFgFU2pThumbImHVsv7oFF5fHkPq1whZyurUQNWx9U";

    private static final Algorithm ALG = Algorithm.HMAC256("secret");

    public static final String VALID = JWT.create()
            .withClaim("Name", "Toninho Araujo")
            .withClaim("Role", "Admin")
            .withClaim("Seed", "7841")
            .sign(ALG);

    public static final String INVALID_NAME = JWT.create()
            .withClaim("Name", "Toninho Araujo")
            .withClaim("Role", "Admin")
            .withClaim("Seed", "170626")
            .sign(ALG);

    public static final String NULL_SEED = JWT.create()
            .withClaim("Name", "Toninho Araujo")
            .withClaim("Role", "Admin")
            .sign(ALG);

    public static final String EXTRA_CLAIM = JWT.create()
            .withClaim("Name", "John")
            .withClaim("Role", "Admin")
            .withClaim("Seed", "7841")
            .withClaim("Extra", "X")     // extra claim
            .sign(ALG);

    public static final String MISSING_ROLE = JWT.create()
            .withClaim("Name", "John")
            .withClaim("Seed", "7841")
            .sign(ALG);

    public static final String NON_PRIME_SEED = JWT.create()
            .withClaim("Name", "Toninho Araujo")
            .withClaim("Role", "Admin")
            .withClaim("Seed", "15")
            .sign(ALG);

    public static final String INVALID_CLAIM_NAME = JWT.create()
            .withClaim("Name", "John")
            .withClaim("Role", "Admin")
            .withClaim("SeedX", "7841") // errado
            .sign(ALG);

    public static final String NULL_CLAIM_VALUE = JWT.create()
            .withClaim("Name", (String) null)
            .withClaim("Role", "Admin")
            .withClaim("Seed", "7841")
            .sign(ALG);

    public static final String CLAIM_NULL = JWT.create()
            .withClaim("Name", "Toninho")
            .withClaim("Role", "Admin")
            .withClaim("Seed", (String) null) // seed null
            .sign(ALG);

    public static final String INVALID_ROLE = JWT.create()
            .withClaim("Name", "Toninho")
            .withClaim("Role", "InvalidRole") // não existe no enum
            .withClaim("Seed", "7841")
            .sign(ALG);


    public static final String ORDER_DIFFERENT = JWT.create()
            .withClaim("Seed", "1117")
            .withClaim("Role", "Member")
            .withClaim("Name", "Maria")
            .sign(ALG);

    public static class JwtPayloadDtoFixtures {

        public static JwtPayloadDto validJwtPayloadDtoFixture() {
            return new JwtPayloadDto("Toninho Araujo", RoleEnum.ADMIN, "7841");
        }

        public static JwtPayloadDto invalidJwtPayloadDtoSeedIsNotAPrimeNumberFixture() {
            return new JwtPayloadDto("Name", RoleEnum.ADMIN, "10");
        }

        public static JwtPayloadDto validAdminFixture() {
            return new JwtPayloadDto("Maria Silva", RoleEnum.ADMIN, "7879");
        }

        public static JwtPayloadDto validMemberFixture() {
            return new JwtPayloadDto("Joao Pereira", RoleEnum.MEMBER, "17");
        }

        public static JwtPayloadDto validExternalFixture() {
            return new JwtPayloadDto("Ana Costa", RoleEnum.EXTERNAL, "11");
        }


        public static JwtPayloadDto nameBlankFixture() {
            return new JwtPayloadDto(" ", RoleEnum.ADMIN, "11");
        }

        public static JwtPayloadDto nameWithNumbersFixture() {
            return new JwtPayloadDto("Joao123", RoleEnum.MEMBER, "17");
        }

        public static JwtPayloadDto nameTooLongFixture() {
            String longName = "A".repeat(300); // >256 chars
            return new JwtPayloadDto(longName, RoleEnum.MEMBER, "11");
        }


        public static JwtPayloadDto roleNullFixture() {
            return new JwtPayloadDto("Joao", null, "17");
        }


        public static JwtPayloadDto seedNotNumericFixture() {
            return new JwtPayloadDto("Joao", RoleEnum.EXTERNAL, "abc123");
        }

        public static JwtPayloadDto seedBlankFixture() {
            return new JwtPayloadDto("Joao", RoleEnum.EXTERNAL, "");
        }

        public static JwtPayloadDto seedNullFixture() {
            return new JwtPayloadDto("Joao", RoleEnum.EXTERNAL, null);
        }


        public static JwtPayloadDto multipleInvalidFieldsFixture() {
            return new JwtPayloadDto(
                    "Joao123",
                    null,
                    "abc"
            );
        }

    }
}
