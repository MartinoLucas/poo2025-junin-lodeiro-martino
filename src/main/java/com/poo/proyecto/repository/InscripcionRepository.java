package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByParticipante_IdAndCompetencia_Id(Long participanteId, Long competenciaId);


}
