package com.poo.proyecto.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/*
 * Clase base abstracta que permite agregar auditoría automática
 * a las entidades del proyecto. Esto significa que registrará
 * automáticamente la fecha de creación y la última fecha de actualización.
 */
@MappedSuperclass // indica que esta clase NO se convertirá en una tabla Sus atributos serán heredados por las entidades hijas.
@EntityListeners(AuditingEntityListener.class) // Permite que Spring Data JPA escuche los eventos de creación y actualización y llene automáticamente los campos createdAt y updatedAt.
public abstract class Auditable {

    @CreatedDate // @CreatedDate indica que este campo se llenará automáticamente con la fecha y hora cuando la entidad se inserta por primera vez.
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate  // indica que este campo se actualiza automáticamente cada vez que la entidad se modifica y se guarda.

    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
