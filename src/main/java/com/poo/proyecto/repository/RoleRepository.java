package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Verifica si ya existe un rol con el mismo nombre (ignora mayúsculas/minúsculas).
     */
    boolean existsByNameIgnoreCase(String name);
}

