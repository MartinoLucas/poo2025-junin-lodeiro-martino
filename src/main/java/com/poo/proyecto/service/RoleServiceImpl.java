package com.poo.proyecto.service;

import com.poo.proyecto.entity.Role;
import com.poo.proyecto.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl extends BaseServiceSupport implements RoleService {

    private final RoleRepository repo;

    public RoleServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public Role create(String name, String description) {
        check(!repo.existsByNameIgnoreCase(name), "Ya existe un role con ese nombre");
        Role r = new Role();
        r.setName(name);
        r.setDescription(description);
        return repo.save(r);
    }

    @Override
    @Transactional
    public Role update(Long id, String name, String description) {
        Role r = orNotFound(repo.findById(id), "Role no encontrado");
        // si cambia el nombre, validar unicidad
        check(r.getName().equalsIgnoreCase(name) || !repo.existsByNameIgnoreCase(name),
                "Nombre de role ya en uso");
        r.setName(name);
        r.setDescription(description);
        return r;
    }

    @Override
    @Transactional(readOnly = true)
    public Role get(Long id) {
        return orNotFound(repo.findById(id), "Role no encontrado");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role r = orNotFound(repo.findById(id), "Role no encontrado");
        repo.delete(r);
    }
}
