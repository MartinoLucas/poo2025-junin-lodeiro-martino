package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Buscar inscripciones por participante
    List<Inscripcion> findByParticipante_Id(Long participanteId);

    // Buscar inscripciones por competencia
    List<Inscripcion> findByCompetencia_Id(Long competenciaId);

    // Verificar si ya está inscripto un participante en una competencia
    Optional<Inscripcion> findByParticipante_IdAndCompetencia_Id(Long participanteId, Long competenciaId);
}

