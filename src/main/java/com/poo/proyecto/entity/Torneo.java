package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.Auditable;
import com.poo.proyecto.entity.state.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "torneo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_torneo_nombre", columnNames = {"nombre"})
        },
        indexes = {
                @Index(name = "ix_torneo_estado", columnList = "estado"),
                @Index(name = "ix_torneo_fechas", columnList = "fecha_inicio, fecha_fin")
        })
public class Torneo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @NotBlank
    @Column(name = "descripcion", nullable = false, length = 2000)
    private String descripcion;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    /** Persistimos el estado como ENUM y lo mapeamos a la estrategia en runtime */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private TorneoStatus estado = TorneoStatus.BORRADOR;

    /* ---------- Métodos utilitarios de dominio (no servicio) ---------- */

    // Mientras "estado" es solo un texto,  "runtimeState" es el OBJETO que tiene la lógica real.

    @Transient
    private TorneoState runtimeState;

    //Cada vez que se carga o guarda el torneo, recreamos el objeto de estado real a partir del ENUM guardado.
    @PostLoad @PostPersist
    private void initState() { this.runtimeState = TorneoStateFactory.from(this.estado); }

    @Transient
    public boolean puedeEditar() { // preguntra si se puede editar, en borrador si
        ensureState();
        return runtimeState.puedeEditar(this);
    }
    //Pregunta si se permiten inscripciones en la fecha PUBLICADO → depende de la fecha
    @Transient
    public boolean puedeInscribir(LocalDate hoy) {
        ensureState();
        return runtimeState.puedeInscribir(this, hoy);
    }
    //Si el runtimeState todavía no existe, lo creamos. Evita errores cuando el objeto no vino de la BD.
    private void ensureState() {
        if (runtimeState == null) runtimeState = TorneoStateFactory.from(this.estado);
    }

    /* getters/setters puros */

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public TorneoStatus getEstado() { return estado; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public void setEstado(TorneoStatus estado) { this.estado = estado; this.runtimeState = TorneoStateFactory.from(estado); }

    public void publicar() {
        ensureState();  // asegura que la clase State sea la misma que el Status
        runtimeState.publicar(this);
    }

    public void finalizar() {
        ensureState();  // asegura que la clase State sea la misma que el Status
        runtimeState.finalizar(this);
    }

    public void eliminar() {
        ensureState();
        if (!runtimeState.puedeEliminar(this)) {
            throw new IllegalStateException("No se puede eliminar el torneo en este estado");
        }
    }

}
