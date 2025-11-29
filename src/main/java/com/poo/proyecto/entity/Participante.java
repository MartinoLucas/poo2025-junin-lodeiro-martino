package com.poo.proyecto.entity;

import com.poo.proyecto.entity.base.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "participant_profile",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_participant_doc", columnNames = {"tipo_documento", "numero_documento"}),
                @UniqueConstraint(name = "uk_participant_user", columnNames = {"user_id"}),
                @UniqueConstraint(name = "uk_participant_email", columnNames = {"email"})
        },
        indexes = {
                @Index(name = "ix_participant_email", columnList = "email")
        })
public class Participante extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_participant_user"))
    private UserAccount userAccount;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(name = "apellido", nullable = false, length = 120)
    private String apellido;

    @Embedded
    private Documento documento;

//    @Embedded
//    @AttributeOverride(name = "value", column = @Column(name = "email", length = 255, nullable = false))
//    private Email email;

    public Long getId() { return id; }
    public UserAccount getUserAccount() { return userAccount; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Documento getDocumento() { return documento; }
//  public Email getEmail() { return email; }

    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setDocumento(Documento documento) { this.documento = documento; }
//  public void setEmail(Email email) { this.email = email; }
}
