package com.poo.proyecto.dto.participante;

import com.poo.proyecto.dto.common.DocumentoDTO;
import com.poo.proyecto.dto.common.EmailDTO;

public class CreateParticipanteDTO {
//    private Long userId;
    private String nombre;
    private String apellido;
    private DocumentoDTO documento;
    private String email;
    private String password;

//    public Long getUserId() { return userId; }
//    public void setUserId(Long userId) { this.userId = userId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public DocumentoDTO getDocumento() { return documento; }
    public void setDocumento(DocumentoDTO documento) { this.documento = documento; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
