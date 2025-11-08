package com.poo.proyecto.dto.inscripcion;

import com.poo.proyecto.dto.common.MoneyDTO;
import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;

import java.time.LocalDateTime;

public class InscripcionResponseDTO {
    private Long id;
    private ParticipanteResponseDTO participante;
    private CompetenciaResponseDTO competencia;
    private MoneyDTO precioPagado;
    private LocalDateTime fechaInscripcion;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParticipanteResponseDTO getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteResponseDTO participante) {
        this.participante = participante;
    }

    public CompetenciaResponseDTO getCompetencia() {
        return competencia;
    }

    public void setCompetencia(CompetenciaResponseDTO competencia) {
        this.competencia = competencia;
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
