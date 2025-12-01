package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Inscripcion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Buscar inscripciones por participante
    Page<Inscripcion> findByParticipante_Id(Long participanteId, Pageable pageable);

    // Buscar inscripciones por competencia
    List<Inscripcion> findByCompetencia_Id(Long competenciaId);

    // Verificar si ya está inscripto un participante en una competencia
    Optional<Inscripcion> findByParticipante_IdAndCompetencia_Id(Long participanteId, Long competenciaId);

    // Cuenta cuántas inscripciones tiene un participante dentro de un torneo.
    int countByParticipante_IdAndCompetencia_Torneo_Id(Long participanteId, Long torneoId);

    // Igual que findByCompetencia_Id pero con paginación.
    Page<Inscripcion> findByCompetencia_Id(Long competenciaId, Pageable pageable);

}

