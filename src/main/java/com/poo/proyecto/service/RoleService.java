package com.poo.proyecto.service;

import com.poo.proyecto.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Role create(String name, String description);
    Role update(Long id, String name, String description);
    Role get(Long id);
    Page<Role> list(Pageable pageable);
    void delete(Long id);
}

