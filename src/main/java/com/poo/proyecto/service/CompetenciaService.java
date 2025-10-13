package com.poo.proyecto.service;

import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.base.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetenciaService {
    Competencia create(Long torneoId, String nombre, Money precioBase, int cupo);
    Competencia update(Long id, String nombre, Money precioBase, int cupo);
    Competencia get(Long id);
    Page<Competencia> listByTorneo(Long torneoId, Pageable pageable);
    void delete(Long id);
}

