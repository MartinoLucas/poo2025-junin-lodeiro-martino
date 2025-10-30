package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Page<Torneo> findAllByEstado(TorneoStatus estado, Pageable pageable);

}
