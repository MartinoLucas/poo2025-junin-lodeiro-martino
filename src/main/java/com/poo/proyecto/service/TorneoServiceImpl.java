package com.poo.proyecto.service;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;

import com.poo.proyecto.mapper.TorneoMapper;
import com.poo.proyecto.repository.CompetenciaRepository;
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
    private final CompetenciaService competenciaService;
    private final InscripcionService inscripcionService;
    private final CompetenciaRepository competenciaRepository;

    public TorneoServiceImpl(TorneoRepository repo, TorneoMapper mapper, CompetenciaService competenciaService, InscripcionService inscripcionService, CompetenciaRepository competenciaRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.competenciaService = competenciaService;
        this.inscripcionService = inscripcionService;
        this.competenciaRepository = competenciaRepository;
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

        if (dto.getNombre() != null && !dto.getNombre().equalsIgnoreCase(t.getNombre())) {
            check(!repo.existsByNombreIgnoreCase(dto.getNombre()), "Ya existe un torneo con ese nombre");
        }

        if (dto.getFechaFin() != null && dto.getFechaInicio() != null) {
            check(!dto.getFechaFin().isBefore(dto.getFechaInicio()), "La fecha fin no puede ser anterior al inicio");
        }

        mapper.updateEntity(dto, t);
        return mapper.toResponse(t);
    }

    @Override @Transactional
    public TorneoResponseDTO publish(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");

        // aplicamos el patron state
        t.publicar();

        return mapper.toResponse(t);
    }

    @Override @Transactional
    public TorneoResponseDTO finalizeTournament(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");

        //aplicamos el patron state
        t.finalizar();

        return mapper.toResponse(t);
    }

    @Override @Transactional
    public void deleteDraft(Long id) {
        Torneo t = orNotFound(repo.findById(id), "Torneo no encontrado");

        t.eliminar();

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

    @Override @Transactional(readOnly = true)
    public Page<TorneoResponseDTO> listAll(Pageable pageable) {
        return repo.findAll(pageable).map(torneo -> {
            TorneoResponseDTO dto = mapper.toResponse(torneo);
            dto.setCantCompetencias(competenciaRepository.countByTorneoId(torneo.getId()));
            return dto;
        });
    }

    @Override @Transactional(readOnly = true)
    public Page<CompetenciaResponseDTO> findAllByTorneoId(Long torneoId, Pageable pageable) {
        return this.competenciaService.listByTorneo(torneoId, pageable);
    }

    @Override @Transactional(readOnly = true)
    public CompetenciaResponseDTO getByTorneoIdByCompetitionId(Long torunamentId, Long id) {
        return this.competenciaService.get(id);
    }

    @Override @Transactional
    public CompetenciaResponseDTO createCompetitionInTournament(Long tournamentId, CreateCompetenciaDTO dto) {
        return this.competenciaService.create(dto);
    }

    @Override @Transactional
    public CompetenciaResponseDTO updateCompetitionInTournament(Long tournamentId, Long competitionId, UpdateCompetenciaDTO dto) {
        return this.competenciaService.update(competitionId, dto);
    }

    @Override
    public Page<InscripcionResponseDTO> listInscriptionsByCompetition(Long tournamentId, Long competitionId, Pageable pageable) {
        //return this.participanteService.listByCompetitionId(competitionId, pageable);
        return this.inscripcionService.listByCompetitionId(competitionId, pageable);
    }
}