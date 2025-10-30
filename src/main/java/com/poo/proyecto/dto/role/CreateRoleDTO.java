package com.poo.proyecto.dto.role;

import jakarta.validation.constraints.NotBlank;

public class CreateRoleDTO {

    @NotBlank
    private String name;

    private String description;

    public CreateRoleDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
