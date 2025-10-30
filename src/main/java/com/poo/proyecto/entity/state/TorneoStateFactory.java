package com.poo.proyecto.entity.state;

import com.poo.proyecto.entity.TorneoStatus;

public final class TorneoStateFactory {
    private TorneoStateFactory() { }

    public static TorneoState from(TorneoStatus status) {
        return switch (status) {
            case BORRADOR -> new TorneoBorrador();
            case PUBLICADO -> new TorneoPublicado();
            case FINALIZADO -> new TorneoFinalizado();
        };
    }
}
