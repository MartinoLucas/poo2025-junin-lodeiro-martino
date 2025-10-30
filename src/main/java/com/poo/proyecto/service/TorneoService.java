package com.poo.proyecto.service;

import com.poo.proyecto.entity.Torneo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TorneoService {

    Torneo create(String nombre, String descripcion, LocalDate inicio,LocalDate fin);
    Torneo update(Long id, String nombre, String descripcion, LocalDate inicio,LocalDate fin);
    Torneo publish(Long id);
    Torneo finalizeTournament(Long id);
    void deleteDraft(Long id);
    Torneo get(Long id);
    Page<Torneo> listPublished(Pageable pageable);
}
