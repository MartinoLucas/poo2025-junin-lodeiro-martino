package com.poo.proyecto.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component // Indica que esta clase es un componente utilitario de Spring.
// Spring la detecta automáticamente y permite inyectarla donde se necesite.
public class JwtTokenUtil {

    private static final String SECRET = "manquesin-barinaga-dilolo-costa-blanco-paredes-delgado-changuito-palacios-merentiel-gimenez";
    private static final long EXPIRATION = 1000L * 60 * 60 * 24 * 10; // 10 días
    private final Algorithm algorithm = Algorithm.HMAC512(SECRET);

    public String generateToken(String subject, List<String> roles, Long userId) {
        String token = JWT.create()
                .withSubject(subject)               // Asigna el "dueño" del token (usuario)
                .withClaim("roles", roles)  // Agrega roles dentro del toke
                .withClaim("sub_id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))  // Fecha de expiración

                .sign(algorithm);   // Firma el token usando HMAC512
        return "Bearer " + token;
    }
    //verifica si el token es valido
    public boolean verify(String token) {
        try {
            JWT.require(algorithm).build().verify(stripPrefix(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //Obtiene el "subject" almacenado en el token.
    public String getSubject(String token) {
        DecodedJWT decoded = JWT.require(algorithm).build().verify(stripPrefix(token));
        return decoded.getSubject();
    }
    //Elimina el prefijo "Bearer " del token si viene incluido es necesario para poder verificarlo correctamente.

    public String stripPrefix(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
