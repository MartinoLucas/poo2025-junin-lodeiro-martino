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

    public InscripcionServiceImpl(InscripcionRepository repo,
                                  ParticipanteRepository participanteRepo,
                                  CompetenciaRepository competenciaRepo, InscripcionMapper mapper) {
        this.repo = repo;
        this.participanteRepo = participanteRepo;
        this.competenciaRepo = competenciaRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public InscripcionResponseDTO create(CreateInscripcionDTO dto) {
        Participante p = orNotFound(participanteRepo.findById(dto.getParticipanteId()), "Participante no encontrado");
        Competencia c = orNotFound(competenciaRepo.findWithTorneoById(dto.getCompetenciaId()), "Competencia no encontrada");

        check(c.hayCupo(), "No hay cupo disponible");
        check(repo.findByParticipante_IdAndCompetencia_Id(dto.getParticipanteId(), dto.getCompetenciaId()).isPresent(),
                "Participante ya inscrito en esta competencia");
        check(dto.getPrecioPagado()!=null, "Precio Pagado no puede ser nulo");
        check(dto.getFechaInscripcion()!=null, "Fecha Inscripcion no puede ser nulo");

        c.incrementarInscriptos();

        Inscripcion i = mapper.toEntity(dto);

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
}
