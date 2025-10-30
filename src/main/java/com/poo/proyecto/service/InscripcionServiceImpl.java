package com.poo.proyecto.service;

import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.base.Money;
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

    public InscripcionServiceImpl(InscripcionRepository repo,
                                  ParticipanteRepository participanteRepo,
                                  CompetenciaRepository competenciaRepo) {
        this.repo = repo;
        this.participanteRepo = participanteRepo;
        this.competenciaRepo = competenciaRepo;
    }

    @Override
    @Transactional
    public Inscripcion create(Long participanteId, Long competenciaId, java.math.BigDecimal precioPagado) {
        Participante p = orNotFound(participanteRepo.findById(participanteId), "Participante no encontrado");
        Competencia c = orNotFound(competenciaRepo.findByIdWithTorneo(competenciaId), "Competencia no encontrada");

        if (!c.hayCupo()) throw new RuntimeException("No hay cupo disponible");
        if (repo.findByParticipante_IdAndCompetencia_Id(participanteId, competenciaId).isPresent())
            throw new RuntimeException("Participante ya inscrito en esta competencia");

        c.incrementarInscriptos();

        Inscripcion i = new Inscripcion();
        i.setParticipante(p);
        i.setCompetencia(c);
        i.setPrecioPagado(Money.of(precioPagado));
        i.setFechaInscripcion(java.time.LocalDateTime.now());
        return repo.save(i);
    }

    @Override
    @Transactional(readOnly = true)
    public Inscripcion get(Long id) {
        return orNotFound(repo.findById(id), "Inscripción no encontrada");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inscripcion> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
