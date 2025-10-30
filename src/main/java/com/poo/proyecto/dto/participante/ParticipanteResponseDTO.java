package com.poo.proyecto.dto.participante;

import com.poo.proyecto.dto.common.DocumentoDTO;
import com.poo.proyecto.dto.common.EmailDTO;

public class ParticipanteResponseDTO {
    private Long id;
    private Long userId;
    private String nombre;
    private String apellido;
    private DocumentoDTO documento;
    private EmailDTO email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public DocumentoDTO getDocumento() { return documento; }
    public void setDocumento(DocumentoDTO documento) { this.documento = documento; }

    public EmailDTO getEmail() { return email; }
    public void setEmail(EmailDTO email) { this.email = email; }
}
