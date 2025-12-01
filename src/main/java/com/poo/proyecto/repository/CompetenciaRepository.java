package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Competencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  //@Repository es una anotación de Spring que marca a la clase o interfaz como parte de la capa de acceso a datos.
//// Le dice a Spring que esta clase se encarga de hablar con la base de datos.

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

    // Busca competencias de un torneo
    // Método paginado
    Page<Competencia> findByTorneo_Id(Long torneoId, Pageable pageable);

    // Verifica si ya existe una competencia con mismo nombre en un torneo
    boolean existsByTorneo_IdAndNombreIgnoreCase(Long torneoId, String nombre);

    // Trae competencia junto con su torneo (para validar estado)
    @EntityGraph(attributePaths = { "torneo" })
    Optional<Competencia> findWithTorneoById(Long id);

}
