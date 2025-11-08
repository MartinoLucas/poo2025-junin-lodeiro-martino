package com.poo.proyecto.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET = "manquesin-barinaga-dilolo-costa-blanco-paredes-delgado-changuito-palacios-merentiel-gimenez";
    private static final long EXPIRATION = 1000L * 60 * 60 * 24 * 10; // 10 días
    private final Algorithm algorithm = Algorithm.HMAC512(SECRET);

    public String generateToken(String subject) {
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(algorithm);
        return "Bearer " + token;
    }

    public boolean verify(String token) {
        try {
            JWT.require(algorithm).build().verify(stripPrefix(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        DecodedJWT decoded = JWT.require(algorithm).build().verify(stripPrefix(token));
        return decoded.getSubject();
    }

    private String stripPrefix(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
