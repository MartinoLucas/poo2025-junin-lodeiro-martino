package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import java.time.LocalDate;

public non-sealed class TorneoFinalizado implements TorneoState {
    @Override public boolean puedeEditar(Torneo ctx) { return false; }
    @Override public boolean puedeInscribir(Torneo ctx, LocalDate hoy) { return false; }
    @Override public boolean puedeEliminar(Torneo ctx) { return false; }

    @Override public void publicar(Torneo ctx) {
        throw new IllegalStateException("No se puede publicar un torneo finalizado.");
    }

    @Override public void finalizar(Torneo ctx) {
        throw new IllegalStateException("El torneo ya está finalizado.");
    }
}
