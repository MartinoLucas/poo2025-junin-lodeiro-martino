package com.poo.proyecto.service;

import com.poo.proyecto.entity.Inscripcion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface InscripcionService {

    Inscripcion create(Long participanteId, Long competenciaId, BigDecimal precioPagado);

    Inscripcion get(Long id);

    Page<Inscripcion> list(Pageable pageable);
}