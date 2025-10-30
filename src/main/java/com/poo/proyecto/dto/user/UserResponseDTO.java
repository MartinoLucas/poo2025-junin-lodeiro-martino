package com.poo.proyecto.dto.user;

import com.poo.proyecto.dto.common.EmailDTO;
import java.time.LocalDateTime;
import java.util.Set;

public class UserResponseDTO {
    private Long id;
    private EmailDTO email;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmailDTO getEmail() { return email; }
    public void setEmail(EmailDTO email) { this.email = email; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
