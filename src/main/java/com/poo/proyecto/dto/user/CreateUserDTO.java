package com.poo.proyecto.dto.user;

import com.poo.proyecto.dto.common.EmailDTO;
import java.util.Set;

public class CreateUserDTO {
    private String email;
    private String password;
    private Set<Long> roleIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(Set<Long> roleIds) { this.roleIds = roleIds; }
}
