package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripcion",
        uniqueConstraints = @UniqueConstraint(name = "uk_insc_part_comp",
                columnNames = {"participante_id", "competencia_id"}),
        indexes = {
                @Index(name = "ix_insc_competencia", columnList = "competencia_id"),
                @Index(name = "ix_insc_participante", columnList = "participante_id")
        })
public class Inscripcion extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_insc_participante"))
    private Participante participante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competencia_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_insc_competencia"))
    private Competencia competencia;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "precio_pagado", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "precio_pagado_currency", length = 3, nullable = false))
    })
    @NotNull
    private Money precioPagado;

    @NotNull
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    public Long getId() { return id; }
    public Participante getParticipante() { return participante; }
    public Competencia getCompetencia() { return competencia; }
    public Money getPrecioPagado() { return precioPagado; }
    public LocalDateTime getFechaInscripcion() { return fechaInscripcion; }

    public void setParticipante(Participante participante) { this.participante = participante; }
    public void setCompetencia(Competencia competencia) { this.competencia = competencia; }
    public void setPrecioPagado(Money precioPagado) { this.precioPagado = precioPagado; }
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}
