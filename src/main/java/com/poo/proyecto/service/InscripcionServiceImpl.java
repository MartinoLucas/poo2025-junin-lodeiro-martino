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
import com.poo.proyecto.repository.UserAccountRepository;
import com.poo.proyecto.service.policy.PrecioInscripcionPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionServiceImpl extends BaseServiceSupport implements InscripcionService {

    private final InscripcionRepository repo;
    private final ParticipanteRepository participanteRepo;
    private final CompetenciaRepository competenciaRepo;
    private final InscripcionMapper mapper;
    private final PrecioInscripcionPolicy precioPolicy;
    private final UserAccountRepository userAccountRepo;
    private final UserAccountService userAccountService;

    public InscripcionServiceImpl(InscripcionRepository repo,
                                  ParticipanteRepository participanteRepo,
                                  CompetenciaRepository competenciaRepo, InscripcionMapper mapper, PrecioInscripcionPolicy precioPolicy, UserAccountRepository userAccountRepo, UserAccountService userAccountService) {
        this.repo = repo;
        this.participanteRepo = participanteRepo;
        this.competenciaRepo = competenciaRepo;
        this.mapper = mapper;
        this.precioPolicy = precioPolicy;
        this.userAccountRepo = userAccountRepo;
        this.userAccountService = userAccountService;
    }

    @Override
    @Transactional
    public InscripcionResponseDTO create(CreateInscripcionDTO dto) {
//        Participante p = orNotFound(participanteRepo.findById(dto.getParticipanteId()), "Participante no encontrado");
        Participante p = new Participante();
        if(participanteRepo.findById(dto.getParticipanteId()).isEmpty()) {
            p = orNotFound(userAccountService.getParticipant(dto.getParticipanteId()), "Usuario no encontrado");
        }
        Competencia c = orNotFound(competenciaRepo.findWithTorneoById(dto.getCompetenciaId()), "Competencia no encontrada");

        check(c.hayCupo(), "No hay cupo disponible");

        check(repo.findByParticipante_IdAndCompetencia_Id(p.getId(), dto.getCompetenciaId()).isEmpty(),
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

        i.setParticipante(p);
        i.setCompetencia(c);
        i.setPrecioPagado(precioFinal);
        i.setFechaInscripcion(dto.getFechaInscripcion());

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
        //TOMAR DEL TOKEN EL ID DEL PARTICIPANTE, SOLO UN USUARIO PUEDE VER SUS INSCRIPCIONES
        Participante p = orNotFound(userAccountService.getParticipant(participanteId),"Usuario no encontrado");
        Page<InscripcionResponseDTO> l = repo.findByParticipante_Id(p.getId(), pageable).map(mapper::toResponse);
        return l;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InscripcionResponseDTO> listByCompetitionId(Long competitionId, Pageable pageable) {
        return repo.findByCompetencia_Id(competitionId, pageable).map(mapper::toResponse);
    }
}
