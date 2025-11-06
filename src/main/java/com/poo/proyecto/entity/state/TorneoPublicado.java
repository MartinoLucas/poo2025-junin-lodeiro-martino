package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;

import java.time.LocalDate;

public non-sealed class TorneoPublicado implements TorneoState {
    @Override public boolean puedeEditar(Torneo ctx) { return true; /* o limitada según negocio */ }
    @Override public boolean puedeInscribir(Torneo ctx, LocalDate hoy) { return hoy.isBefore(ctx.getFechaInicio()); }
    @Override public boolean puedeEliminar(Torneo ctx) {
        return false; // No se puede eliminar un torneo publicado
    }

    @Override public void publicar(Torneo ctx) {
        throw new IllegalStateException("El torneo ya está publicado.");
    }

    @Override public void finalizar(Torneo ctx) {
        ctx.setEstado(TorneoStatus.FINALIZADO);
    }
}
