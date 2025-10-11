package com.poo.proyecto.service.policy;

import com.poo.proyecto.entity.base.Money;

/**

 * Strategy para calcular el precio final de una inscripción.
 * La idea: poder reemplazarla por impuestos, promos, etc., sin tocar el servicio.*/
public interface PrecioInscripcionPolicy {
    /**

    * @param precioBase precio base de la competencia
    * @param inscripcionesPreviasEnMismoTorneo cuántas inscripciones previas tiene el participante en el mismo torneo*/
    Money calcular(Money precioBase, long inscripcionesPreviasEnMismoTorneo);
}