package com.azimsh3r.apiservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("jwt-secret")
    private String secret;

    public String generateToken(String token) {
        return JWT.create()
                .withSubject("NOTIFICATION_SERVICE")
                .withClaim("token", token)
                .withIssuedAt(new Date())
                .withIssuer("NOTIFICATION_SERVICE")
                .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(5).toInstant()))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveData(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("NOTIFICATION_SERVICE")
                .withSubject("NOTIFICATION_SERVICE")
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("token").asString();
    }
}
