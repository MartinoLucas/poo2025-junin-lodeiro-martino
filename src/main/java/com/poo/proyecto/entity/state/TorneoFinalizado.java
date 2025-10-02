package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import java.time.LocalDate;

public class TorneoFinalizado implements TorneoState {
    @Override public boolean puedeEditar(Torneo ctx) { return false; }
    @Override public boolean puedeInscribir(Torneo ctx, LocalDate hoy) { return false; }
}
