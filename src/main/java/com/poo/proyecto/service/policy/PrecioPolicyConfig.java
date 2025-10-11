package com.poo.proyecto.service.policy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wiring de la estrategia actual (cambiable a futuro) */
@Configuration
public class PrecioPolicyConfig {
    @Bean
    public PrecioInscripcionPolicy precioInscripcionPolicy() {
        return new Precio50DespuesDeLaPrimera();
    }
}