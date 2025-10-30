package com.poo.proyecto.service;

import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;

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

    public TorneoServiceImpl(TorneoRepository repo) {
        this.repo = repo;
    }

    @Override @Transactional
    public Torneo create(String nombre, String descripcion, LocalDate inicio, LocalDate fin) {
        check(!fin.isBefore(inicio), "La fecha fin no puede ser anterior al inicio");
        check(!repo.existsByNombreIgnoreCase(nombre), "Ya existe un torneo con ese nombre");

        Torneo t = new Torneo();
        t.setNombre(nombre);
        t.setDescripcion(descripcion);
        t.setFechaInicio(inicio);
        t.setFechaFin(fin);
        t.setEstado(TorneoStatus.BORRADOR);
        return repo.save(t);
    }

    @Override @Transactional
    public Torneo update(Long id, String nombre, String descripcion, LocalDate inicio, LocalDate fin) {
        Torneo t = get(id);
        check(t.puedeEditar(), "El torneo no permite edición en este estado");
        check(!fin.isBefore(inicio), "La fecha fin no puede ser anterior al inicio");

        t.setNombre(nombre);
        t.setDescripcion(descripcion);
        t.setFechaInicio(inicio);
        t.setFechaFin(fin);
        return t; // dirty checking
    }

    @Override @Transactional
    public Torneo publish(Long id) {
        Torneo t = get(id);
        check(t.getEstado() == TorneoStatus.BORRADOR, "Solo un borrador puede publicarse");
        t.setEstado(TorneoStatus.PUBLICADO);
        return t;
    }

    @Override @Transactional
    public Torneo finalizeTournament(Long id) {
        Torneo t = get(id);
        if (t.getEstado() == TorneoStatus.FINALIZADO) return t;
        t.setEstado(TorneoStatus.FINALIZADO);
        return t;
    }

    @Override @Transactional
    public void deleteDraft(Long id) {
        Torneo t = get(id);
        check(t.getEstado() == TorneoStatus.BORRADOR, "Solo se puede eliminar un torneo en borrador");
        repo.delete(t);
    }

    @Override @Transactional(readOnly = true)
    public Torneo get(Long id) {
        return orNotFound(repo.findById(id), "Torneo no encontrado");
    }

    @Override @Transactional(readOnly = true)
    public Page<Torneo> listPublished(Pageable pageable) {
        return repo.findAllByEstado(TorneoStatus.PUBLICADO, pageable);

    }


}