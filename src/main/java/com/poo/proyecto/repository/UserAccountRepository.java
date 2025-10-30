package com.poo.proyecto.repository;

import com.poo.proyecto.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Verifica si ya existe una cuenta con el email indicado.
     * Como Email es un embeddable, accedemos a su campo interno 'value'.
     */
    boolean existsByEmail_Value(String email);

    /**
     * Verifica si ya existe una cuenta con el email indicado y que no esté marcada como eliminada (deletedAt is null).
     */
    boolean existsByEmailValueAndDeletedAtIsNull(String email);

    /**
     * Busca una cuenta de usuario por su email (case-insensitive).
     */
    Optional<UserAccount> findByEmail_ValueIgnoreCase(String email);
}

