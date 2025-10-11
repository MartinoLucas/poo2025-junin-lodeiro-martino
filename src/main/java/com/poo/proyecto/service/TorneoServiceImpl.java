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
public class TorneoServiceImpl implements TorneoService {

    private final TorneoRepository repo;

    public TorneoServiceImpl(TorneoRepository repo) {
        this.repo = repo;
    }

    @Override @Transactional
    public Torneo create(String nombre, String descripcion, LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) throw new RuntimeException("La fecha fin no puede ser anterior al inicio");
        if (repo.existsByNombreIgnoreCase(nombre)) throw new RuntimeException("Ya existe un torneo con ese nombre");

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
        if (!t.puedeEditar()) throw new RuntimeException("El torneo no permite ediciÃ³n en este estado");
        if (fin.isBefore(inicio)) throw new RuntimeException("La fecha fin no puede ser anterior al inicio");

        t.setNombre(nombre);
        t.setDescripcion(descripcion);
        t.setFechaInicio(inicio);
        t.setFechaFin(fin);
        return t; // dirty checking
    }

    @Override @Transactional
    public Torneo publish(Long id) {
        Torneo t = get(id);
        if (t.getEstado() != TorneoStatus.BORRADOR) throw new RuntimeException("Solo un borrador puede publicarse");
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
        if (t.getEstado() != TorneoStatus.BORRADOR)
            throw new RuntimeException("Solo se puede eliminar un torneo en borrador");
        repo.delete(t);
    }

    @Override @Transactional(readOnly = true)
    public Torneo get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Torneo no encontrado"));
    }

    @Override @Transactional(readOnly = true)
    public Page<Torneo> listPublished(Pageable pageable) {
        return new Page<Torneo>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Torneo, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Torneo> getContent() {
                return List.of();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Torneo> iterator() {
                return null;
            }
        };
    }


}