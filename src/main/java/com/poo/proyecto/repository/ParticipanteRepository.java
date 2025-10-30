package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    // Buscar participante por documento
    Optional<Participante> findByDocumento_TipoAndDocumento_Numero(String tipo, String numero);

    // Buscar participante por user account
    Optional<Participante> findByUserAccount_Id(Long userId);
}

