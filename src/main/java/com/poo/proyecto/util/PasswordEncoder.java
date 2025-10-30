package com.poo.proyecto.util;

import com.password4j.Password;


public class PasswordEncoder {

    /**
     * Recibe un password en texto plano y retorna un hash de Bcrypt.
     */
    public String encode(String rawPassword) {
        if (rawPassword == null) return null;
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    /**
     * Verifica que rawPassword cuadre con encodedPassword.
     */
    public boolean verify(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) return false;
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }
}