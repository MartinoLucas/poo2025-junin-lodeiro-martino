package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.Auditable;
import com.poo.proyecto.entity.base.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_account",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"))
public class UserAccount extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, length = 255))
    private Email email;

    @NotBlank
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /** Soft delete: activo si deleted_at IS NULL */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_roles_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_roles_role"))
    )
    private Set<Role> roles = new HashSet<>();

    public Long getId() { return id; }
    public Email getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public Set<Role> getRoles() { return roles; }

    public void setEmail(Email email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    @Transient
    public boolean isActive() { return deletedAt == null; }
}
