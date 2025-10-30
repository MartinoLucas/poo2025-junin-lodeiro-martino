package com.poo.proyecto.service;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.base.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetenciaService {
    CompetenciaResponseDTO create(CreateCompetenciaDTO dto);
    CompetenciaResponseDTO update(Long id, UpdateCompetenciaDTO dto);
    CompetenciaResponseDTO get(Long id);
    Page<CompetenciaResponseDTO> listByTorneo(Long torneoId, Pageable pageable);
    void delete(Long id);
}

