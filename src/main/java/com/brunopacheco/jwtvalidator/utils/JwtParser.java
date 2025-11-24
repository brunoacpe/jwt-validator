package com.brunopacheco.jwtvalidator.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brunopacheco.jwtvalidator.dto.JwtPayloadDto;
import com.brunopacheco.jwtvalidator.enums.ClaimsEnum;
import com.brunopacheco.jwtvalidator.enums.RoleEnum;
import com.brunopacheco.jwtvalidator.exception.InvalidJwtException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class JwtParser {

    public JwtPayloadDto parse(String jwt) {
        try {
            DecodedJWT decoded = JWT.decode(jwt);

            validateClaims(decoded);

            String name = decoded.getClaim(ClaimsEnum.NAME.getKey()).asString();
            String roleString = decoded.getClaim(ClaimsEnum.ROLE.getKey()).asString();
            String seed = decoded.getClaim(ClaimsEnum.SEED.getKey()).asString();

            validateNullClaims(name, seed, roleString);

            RoleEnum role = RoleEnum.fromJwtValue(roleString);

            return new JwtPayloadDto(name, role, seed);

        } catch (InvalidJwtException e) {
            throw e;
        }
        catch (Exception e) {
            throw new InvalidJwtException("Malformed or invalid JWT: " + e.getMessage());
        }
    }


    private void validateClaims(DecodedJWT decoded) {
        Map<String, ?> claims = decoded.getClaims();
        Set<String> jwtKeys = claims.keySet();
        Set<String> validKeys = ClaimsEnum.validKeys();

        if (!jwtKeys.containsAll(validKeys)) {
            throw new InvalidJwtException("Token is missing required claims");
        }


        if (!validKeys.containsAll(jwtKeys)) {
            throw new InvalidJwtException("Token contains invalid or unexpected claims");
        }
    }

    private void validateNullClaims(String... claims) {
        for (String claim : claims) {
            if (claim == null)
                throw new InvalidJwtException("Missing required claims");
        }
    }
}
