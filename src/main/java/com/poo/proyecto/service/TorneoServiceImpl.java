package com.poo.proyecto.service;

import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;

import com.poo.proyecto.mapper.TorneoMapper;
import com.poo.proyecto.repository.TorneoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Service
public class TorneoServiceImpl  extends BaseServiceSupport implements TorneoService {

    private final TorneoRepository repo;
    private final TorneoMapper mapper;

    public TorneoServiceImpl(TorneoRepository repo, TorneoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override @Transactional
    public TorneoResponseDTO create(CreateTorneoDTO dto) {
        check(!dto.getFechaFin().isBefore(dto.getFechaInicio()), "La fecha fin no puede ser anterior al inicio");
        check(!repo.existsByNombreIgnoreCase(dto.getNombre()), "Ya existe un torneo con ese nombre");

        Torneo t = mapper.toEntity(dto);
        repo.save(t);
        return mapper.toResponse(t);
    }

    @Override @Transactional
    public TorneoResponseDTO update(Long id, UpdateTorneoDTO dto) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");
        check(t.puedeEditar(), "El torneo no permite edición en este estado");
        check(!dto.getFechaFin().isBefore(dto.getFechaInicio()), "La fecha fin no puede ser anterior al inicio");

        mapper.updateEntity(dto, t);
        return mapper.toResponse(t);
    }

    @Override @Transactional
    public TorneoResponseDTO publish(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");
        check(t.getEstado() == TorneoStatus.BORRADOR, "Solo un borrador puede publicarse");
        t.setEstado(TorneoStatus.PUBLICADO);
        return mapper.toResponse(t);
    }

    @Override @Transactional
    public TorneoResponseDTO finalizeTournament(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");
        check(t.getEstado() == TorneoStatus.PUBLICADO, "Solo un torneo publicado puede finalizarse");
        check(t.getEstado() == TorneoStatus.FINALIZADO, "El torneo ya está finalizado");
        t.setEstado(TorneoStatus.FINALIZADO);
        return mapper.toResponse(t);
    }

    @Override @Transactional
    public void deleteDraft(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");
        check(t.getEstado() == TorneoStatus.BORRADOR, "Solo se puede eliminar un torneo en borrador");
        repo.delete(t);
    }

    @Override @Transactional(readOnly = true)
    public TorneoResponseDTO get(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");
        return mapper.toResponse(t);
    }

    @Override @Transactional(readOnly = true)
    public Page<TorneoResponseDTO> listPublished(Pageable pageable) {
        return repo.findAllByEstado(TorneoStatus.PUBLICADO, pageable).map(mapper::toResponse);
    }
}