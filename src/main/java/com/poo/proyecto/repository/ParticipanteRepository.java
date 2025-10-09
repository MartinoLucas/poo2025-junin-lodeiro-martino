package com.poo.proyecto.repository;


import com.poo.proyecto.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}
