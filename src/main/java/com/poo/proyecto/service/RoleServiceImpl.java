package com.poo.proyecto.service;

import com.poo.proyecto.dto.role.CreateRoleDTO;
import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.dto.role.UpdateRoleDTO;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.mapper.RoleMapper;
import com.poo.proyecto.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl extends BaseServiceSupport implements RoleService {

    private final RoleRepository repo;
    private final RoleMapper mapper;

    public RoleServiceImpl(RoleRepository repo, RoleMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public RoleResponseDTO create(CreateRoleDTO dto) {
        check(!repo.existsByNameIgnoreCase(dto.getName()), "Ya existe un role con ese nombre");

        Role r = mapper.toEntity(dto);
        return mapper.toResponse(repo.save(r));
    }

    @Override
    @Transactional
    public RoleResponseDTO update(Long id, UpdateRoleDTO dto) {
        Role r = orNotFound(repo.findById(id), "Role no encontrado");

        // si cambia el nombre, validar unicidad
        check(r.getName().equalsIgnoreCase(dto.getName()) || !repo.existsByNameIgnoreCase(dto.getName()),
                "Nombre de role ya en uso");

        mapper.updateEntity(dto, r);
        return mapper.toResponse(r);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO get(Long id) {
        Role r = orNotFound(repo.findById(id), "Role no encontrado");
        return mapper.toResponse(r);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleResponseDTO> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role r = orNotFound(repo.findById(id), "Role no encontrado");
        repo.delete(r);
    }
}
