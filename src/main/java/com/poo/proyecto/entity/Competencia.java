package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "competencia",
        uniqueConstraints = @UniqueConstraint(name = "uk_competencia_torneo_nombre", columnNames = {"torneo_id", "nombre"}),
        indexes = @Index(name = "ix_competencia_torneo", columnList = "torneo_id"))
public class Competencia extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Identificador único autogenerado por la base de datos
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "torneo_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_competencia_torneo"))

    // Relación N competencas → 1 torneo
    // Lazy loading para no cargar el torneo completo innecesariamente
    private Torneo torneo;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "precio_base", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "precio_base_currency", length = 3, nullable = false))
    })
    @Valid
    private Money precioBase;

    @Min(1)
    @Column(name = "cupo", nullable = false)
    private int cupo;

    @Column(name = "inscriptos_actuales", nullable = false)
    private int inscriptosActuales = 0;

    /** Locking optimista para colisiones de cupo */
    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public Long getId() { return id; }
    public Torneo getTorneo() { return torneo; }
    public String getNombre() { return nombre; }
    public Money getPrecioBase() { return precioBase; }
    public int getCupo() { return cupo; }
    public int getInscriptosActuales() { return inscriptosActuales; }
    public int getVersion() { return version; }

    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecioBase(Money precioBase) { this.precioBase = precioBase; }
    public void setCupo(int cupo) { this.cupo = cupo; }

    @Transient
    public boolean hayCupo() { return inscriptosActuales < cupo; }

    public void incrementarInscriptos() {
        if (!hayCupo()) throw new IllegalStateException("Cupo agotado");
        this.inscriptosActuales += 1;
    }
}
