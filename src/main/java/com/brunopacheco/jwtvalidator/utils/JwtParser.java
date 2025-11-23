package com.brunopacheco.jwtvalidator.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtParser {
    private JwtParser(){}

    public JwtPayloadDto parse(String jwt) {
        try {
            DecodedJWT decoded = JWT.decode(jwt);

            if (decoded.getClaims().size() != 3) {
                throw new InvalidJwtException("Token must contain exactly 3 claims");
            }

            String name = decoded.getClaim("Name").asString();
            String role = decoded.getClaim("Role").asString();
            String seed = decoded.getClaim("Seed").asString();

            if (name == null || role == null || seed == null) {
                throw new InvalidJwtException("Missing required claims");
            }

            return new JwtPayloadDto(name, role, seed);

        } catch (Exception e) {
            throw new InvalidJwtException("Malformed or invalid JWT: " + e.getMessage());
        }
    }
}
