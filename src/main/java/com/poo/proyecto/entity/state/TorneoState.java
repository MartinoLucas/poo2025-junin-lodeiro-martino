package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import java.time.LocalDate;

public interface TorneoState {
    boolean puedeEditar(Torneo ctx);
    boolean puedeInscribir(Torneo ctx, LocalDate hoy);
}
