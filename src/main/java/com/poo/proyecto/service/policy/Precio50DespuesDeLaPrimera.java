package com.poo.proyecto.service.policy;

import com.poo.proyecto.entity.base.Money;
import java.math.BigDecimal;

public class Precio50DespuesDeLaPrimera implements PrecioInscripcionPolicy {

    private static final BigDecimal HALF = new BigDecimal("0.50");

    @Override
    public Money calcular(Money precioBase, long previas) {
        if (previas <= 0) return precioBase;                  // primera competencia: 100%
        return precioBase.multiply(HALF);                     // siguientes: 50%
    }
}