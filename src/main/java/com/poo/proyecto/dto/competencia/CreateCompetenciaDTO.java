package com.poo.proyecto.dto.competencia;

import com.poo.proyecto.dto.common.MoneyDTO;

public class CreateCompetenciaDTO {
    private Long torneoId;
    private String nombre;
    private MoneyDTO precioBase;
    private int cupo;

    // getters y setters

    public Long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
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
}
