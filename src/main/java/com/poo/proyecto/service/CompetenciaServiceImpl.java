package com.poo.proyecto.service;

import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.entity.TorneoStatus;
import com.poo.proyecto.entity.base.Money;
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

    public CompetenciaServiceImpl(CompetenciaRepository repo, TorneoRepository torneoRepo) {
        this.repo = repo;
        this.torneoRepo = torneoRepo;
    }

    @Override
    @Transactional
    public Competencia create(Long torneoId, String nombre, Money precioBase, int cupo) {
        Torneo torneo = orNotFound(torneoRepo.findById(torneoId), "Torneo no encontrado");

        check(torneo.getEstado() == TorneoStatus.BORRADOR,
                "Solo se pueden agregar competencias en BORRADOR");

        check(!repo.existsByTorneo_IdAndNombreIgnoreCase(torneoId, nombre),
                "Ya existe una competencia con ese nombre en el torneo");

        Competencia c = new Competencia();
        c.setTorneo(torneo);
        c.setNombre(nombre);
        c.setPrecioBase(precioBase);
        c.setCupo(cupo);
        return repo.save(c);
    }

    @Override
    @Transactional
    public Competencia update(Long id, String nombre, Money precioBase, int cupo) {
        Competencia c = orNotFound(repo.findByIdWithTorneo(id), "Competencia no encontrada");

        check(c.getTorneo().getEstado() == TorneoStatus.BORRADOR,
                "Solo se pueden editar competencias en torneos BORRADOR");

        check(c.getNombre().equalsIgnoreCase(nombre) ||
                        !repo.existsByTorneo_IdAndNombreIgnoreCase(c.getTorneo().getId(), nombre),
                "Nombre de competencia ya en uso en este torneo");

        c.setNombre(nombre);
        c.setPrecioBase(precioBase);
        c.setCupo(cupo);
        return c;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Competencia c = orNotFound(repo.findByIdWithTorneo(id), "Competencia no encontrada");

        check(c.getTorneo().getEstado() == TorneoStatus.BORRADOR,
                "No se puede borrar: torneo no está en BORRADOR");

        repo.delete(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Competencia get(Long id) {
        return orNotFound(repo.findById(id), "Competencia no encontrada");
    }



    @Override
    @Transactional(readOnly = true)
    public Page<Competencia> listByTorneo(Long torneoId, Pageable pageable) {
        return repo.findByTorneo_Id(torneoId, pageable);
    }
}


