package com.brunopacheco.jwtvalidator.integration;

import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtValidationIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    @Test
    void shouldReturnVerdadeiroForValidToken() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.VALID_JWT_TOKEN)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("verdadeiro"));
    }


    @Test
    void shouldReturnFalsoForMalformedToken() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.INVALID_JWT_TOKEN)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldRejectTokenWithUnexpectedClaims() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.EXTRA_CLAIM)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldRejectTokenWithMissingClaims() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.MISSING_ROLE)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldRejectTokenWithNonPrimeSeed() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.NON_PRIME_SEED)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }

    @Test
    void shouldRejectTokenWithInvalidRole() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.INVALID_ROLE)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldRejectTokenWithInvalidName() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.INVALID_NAME)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldRejectTokenWithNullSeed() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.NULL_SEED)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("falso"));
    }


    @Test
    void shouldAcceptTokenWithClaimsOutOfOrder() {
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(Fixtures.ORDER_DIFFERENT)
                .when()
                .post("/api/v1/validate/jwt")
                .then()
                .statusCode(200)
                .body(equalTo("verdadeiro"));
    }
}
