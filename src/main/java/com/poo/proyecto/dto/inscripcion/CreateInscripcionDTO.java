package com.poo.proyecto.dto.inscripcion;

import com.poo.proyecto.dto.common.MoneyDTO;
import java.time.LocalDateTime;

public class CreateInscripcionDTO {
    private Long participanteId;
    private Long competenciaId;
    private MoneyDTO precioPagado;
    private LocalDateTime fechaInscripcion;

    // getters y setters

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public Long getCompetenciaId() {
        return competenciaId;
    }

    public void setCompetenciaId(Long competenciaId) {
        this.competenciaId = competenciaId;
    }

    public MoneyDTO getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(MoneyDTO precioPagado) {
        this.precioPagado = precioPagado;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
