package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    // Buscar participante por documento
    Optional<Participante> findByDocumento_TipoAndDocumento_Numero(TipoDocumento tipo, String numero);

    // Buscar participante por user account
    Optional<Participante> findByUserAccount_Id(Long userId);

    boolean existsByUserAccount_Id(Long userId);

    boolean existsByDocumento_TipoAndDocumento_Numero(TipoDocumento tipo, String numero);

    boolean existsByEmail_Value(String email);
}

