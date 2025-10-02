package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "role",
        uniqueConstraints = @UniqueConstraint(name = "uk_role_name", columnNames = "name"))
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Convención: "ROLE_ADMIN", "ROLE_PARTICIPANTE", "ROLE_GESTOR_COMPETENCIAS", ... */
    @NotBlank
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}
