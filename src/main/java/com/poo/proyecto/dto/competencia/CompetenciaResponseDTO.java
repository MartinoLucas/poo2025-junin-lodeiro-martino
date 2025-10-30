package com.poo.proyecto.dto.competencia;

import com.poo.proyecto.dto.common.MoneyDTO;
import java.time.LocalDateTime;

public class CompetenciaResponseDTO {
    private Long id;
    private String nombre;
    private MoneyDTO precioBase;
    private int cupo;
    private int inscriptosActuales;
    private Long torneoId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MoneyDTO getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(MoneyDTO precioBase) {
        this.precioBase = precioBase;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getInscriptosActuales() {
        return inscriptosActuales;
    }

    public void setInscriptosActuales(int inscriptosActuales) {
        this.inscriptosActuales = inscriptosActuales;
    }

    public Long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
