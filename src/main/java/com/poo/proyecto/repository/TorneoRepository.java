package com.poo.proyecto.repository;

import com.poo.proyecto.entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    boolean existsByNombreIgnoreCase(String nombre);


}
