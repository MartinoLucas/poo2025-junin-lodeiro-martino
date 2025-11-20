package com.poo.proyecto.service;

import com.poo.proyecto.dto.inscripcion.CreateInscripcionDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.base.Money;
import com.poo.proyecto.mapper.InscripcionMapper;
import com.poo.proyecto.repository.CompetenciaRepository;
import com.poo.proyecto.repository.InscripcionRepository;
import com.poo.proyecto.repository.ParticipanteRepository;
import com.poo.proyecto.service.policy.PrecioInscripcionPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscripcionServiceImpl extends BaseServiceSupport implements InscripcionService {

    private final InscripcionRepository repo;
    private final ParticipanteRepository participanteRepo;
    private final CompetenciaRepository competenciaRepo;
    private final InscripcionMapper mapper;
    private final PrecioInscripcionPolicy precioPolicy;

    public InscripcionServiceImpl(InscripcionRepository repo,
                                  ParticipanteRepository participanteRepo,
                                  CompetenciaRepository competenciaRepo, InscripcionMapper mapper, PrecioInscripcionPolicy precioPolicy) {
        this.repo = repo;
        this.participanteRepo = participanteRepo;
        this.competenciaRepo = competenciaRepo;
        this.mapper = mapper;
        this.precioPolicy = precioPolicy;
    }

    @Override
    @Transactional
    public InscripcionResponseDTO create(CreateInscripcionDTO dto) {
        Participante p = orNotFound(participanteRepo.findById(dto.getParticipanteId()), "Participante no encontrado");
        Competencia c = orNotFound(competenciaRepo.findWithTorneoById(dto.getCompetenciaId()), "Competencia no encontrada");

        check(c.hayCupo(), "No hay cupo disponible");

        check(repo.findByParticipante_IdAndCompetencia_Id(dto.getParticipanteId(), dto.getCompetenciaId()).isPresent(),
                "Participante ya inscrito en esta competencia");

        // Calcular si tiene inscripciones previas en el mismo torneo
        long previas = repo.countByParticipante_IdAndCompetencia_Torneo_Id(
                p.getId(),
                c.getTorneo().getId()
        );

        // strategy del precio
        Money precioFinal = precioPolicy.calcular(c.getPrecioBase(), previas);

        check(dto.getFechaInscripcion().isBefore(c.getTorneo().getFechaInicio().atStartOfDay()), "No se puede inscribir después de que inicie el torneo");

        c.incrementarInscriptos();

        Inscripcion i = mapper.toEntity(dto);

        i.setPrecioPagado(precioFinal);

        return mapper.toResponse(repo.save(i));
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponseDTO get(Long id) {
        Inscripcion i = orNotFound(repo.findById(id), "Inscripción no encontrada");
        return mapper.toResponse(i);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InscripcionResponseDTO> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InscripcionResponseDTO> listByParticipanteId(Long participanteId, Pageable pageable) {
        return repo.findByParticipante_Id(participanteId, pageable).map(mapper::toResponse);
    }
}
