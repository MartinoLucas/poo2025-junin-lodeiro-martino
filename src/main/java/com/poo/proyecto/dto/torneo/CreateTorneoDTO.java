package com.poo.proyecto.dto.torneo;

import com.poo.proyecto.entity.TorneoStatus;
import java.time.LocalDate;

public class CreateTorneoDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TorneoStatus estado;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public TorneoStatus getEstado() { return estado; }
    public void setEstado(TorneoStatus estado) { this.estado = estado; }
}
