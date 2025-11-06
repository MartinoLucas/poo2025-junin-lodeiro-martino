package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.Torneo;
import java.time.LocalDate;

public sealed interface TorneoState permits TorneoBorrador, TorneoPublicado, TorneoFinalizado {
    boolean puedeEditar(Torneo ctx);
    boolean puedeInscribir(Torneo ctx, LocalDate hoy);
    boolean puedeEliminar(Torneo ctx);

    //transiciones de estado
    void publicar(Torneo ctx);
    void finalizar(Torneo ctx);
}
