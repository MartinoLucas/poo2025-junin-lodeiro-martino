package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;

import java.time.LocalDate;

public non-sealed class TorneoBorrador implements TorneoState {
    @Override public boolean puedeEditar(Torneo ctx) { return true; }
    @Override public boolean puedeInscribir(Torneo ctx, LocalDate hoy) { return false; }
    @Override public boolean puedeEliminar(Torneo ctx) { return true; }

    @Override public void publicar(Torneo ctx) {
        ctx.setEstado(TorneoStatus.PUBLICADO);
    }

    @Override public void finalizar(Torneo ctx) {
        throw new IllegalStateException("No se puede finalizar un torneo en estado Borrador.");
    }
}
