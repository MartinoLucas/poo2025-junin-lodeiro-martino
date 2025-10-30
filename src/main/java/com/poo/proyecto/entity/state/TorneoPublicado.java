package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import java.time.LocalDate;

public class TorneoPublicado implements TorneoState {
    @Override public boolean puedeEditar(Torneo ctx) { return true; /* o limitada según negocio */ }
    @Override public boolean puedeInscribir(Torneo ctx, LocalDate hoy) { return hoy.isBefore(ctx.getFechaInicio()); }
}
