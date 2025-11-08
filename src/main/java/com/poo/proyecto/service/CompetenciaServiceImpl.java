package com.poo.proyecto.service;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;
import com.poo.proyecto.entity.base.Money;
import com.poo.proyecto.mapper.CompetenciaMapper;
import com.poo.proyecto.repository.CompetenciaRepository;
import com.poo.proyecto.repository.TorneoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetenciaServiceImpl extends BaseServiceSupport implements CompetenciaService {

    private final CompetenciaRepository repo;
    private final TorneoRepository torneoRepo;
    private final CompetenciaMapper mapper;

    public CompetenciaServiceImpl(CompetenciaRepository repo, TorneoRepository torneoRepo, CompetenciaMapper mapper) {
        this.repo = repo;
        this.torneoRepo = torneoRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CompetenciaResponseDTO create(CreateCompetenciaDTO dto) {
        Torneo torneo = orNotFound(torneoRepo.findById(dto.getTorneoId()), "Torneo no encontrado");

        check(torneo.getEstado() == TorneoStatus.BORRADOR,
                "Solo se pueden agregar competencias a torneos en BORRADOR");

        check(!repo.existsByTorneo_IdAndNombreIgnoreCase(dto.getTorneoId(), dto.getNombre()),
                "Ya existe una competencia con ese nombre en el torneo");

        Competencia c = mapper.toEntity(dto);

        return mapper.toResponse(repo.save(c));
    }

    @Override
    @Transactional
    public CompetenciaResponseDTO update(Long id, UpdateCompetenciaDTO dto) {
        Competencia c = orNotFound(repo.findWithTorneoById(id), "Competencia no encontrada");

        check(c.getTorneo().getEstado() == TorneoStatus.BORRADOR,
                "Solo se pueden editar competencias en torneos en BORRADOR");

        if (dto.getNombre() != null) {
            check(!repo.existsByTorneo_IdAndNombreIgnoreCase(c.getTorneo().getId(), dto.getNombre()),
                    "Nombre de competencia ya en uso en este torneo");
        }

        if (dto.getTorneoId() != null) {
            Torneo newTorneo = orNotFound(torneoRepo.findById(dto.getTorneoId()), "Torneo no encontrado");

            check(newTorneo.getEstado() == TorneoStatus.BORRADOR,
                    "Solo se pueden asociar competencias a torneos en BORRADOR");

            check(!repo.existsByTorneo_IdAndNombreIgnoreCase(dto.getTorneoId(),
                    dto.getNombre() != null ? dto.getNombre() : c.getNombre()),
                    "Ya existe una competencia con ese nombre en el nuevo torneo");
        }

        mapper.updateEntity(dto, c);

        return mapper.toResponse(c);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Competencia c = orNotFound(repo.findWithTorneoById(id), "Competencia no encontrada");

        check(c.getTorneo().getEstado() == TorneoStatus.BORRADOR,
                "No se puede borrar: torneo no está en BORRADOR");

        repo.delete(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetenciaResponseDTO get(Long id) {
        Competencia c = orNotFound(repo.findById(id), "Competencia no encontrada");
        return mapper.toResponse(c);
    }



    @Override
    @Transactional(readOnly = true)
    public Page<CompetenciaResponseDTO> listByTorneo(Long torneoId, Pageable pageable) {
        return repo.findByTorneo_Id(torneoId, pageable).map(mapper::toResponse);
    }
}


